package eg.edu.alexu.csd.oop.db.cs58;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs58.storage.Storage;
import eg.edu.alexu.csd.oop.db.cs58.storage.XML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseManager implements Database {

    private static DatabaseManager databaseManager;
    private SyntaxChecker syntaxChecker = new SyntaxChecker();
    private DirectoryHandler directoryHandler = new DirectoryHandler();
    private String fileSeparator = System.getProperty("file.separator");
    private Informations informations = Informations.getInstance();
    private ImplementUpdateQuery implementUpdateQuery;
    private StringQuery stringQuery = new StringQuery();
    private Select select = new Select();
    private Storage storage = new Storage(); // for just loading and validating and not used for save
    private Print printTable = new Print();
    private XML xml = new XML();
    //the Singleton design pattern
    private DatabaseManager() {
        informations.setDatabaseName(null);
    }

    public static DatabaseManager getInstance() {

        if (databaseManager == null) {
            databaseManager = new DatabaseManager();

        }
        return databaseManager;
    }

    public String createDatabase(String databaseName, boolean dropIfExists) {
        informations.setDatabaseName(databaseName);
        String directory = "Databases" + fileSeparator + databaseName;
        if(!new File("Databases").exists())
        {
            new File("Databases").mkdirs();
        }

        if (!(new File(directory).mkdirs())) {
            if (dropIfExists) {
                /*
                    We remove the existing database and create a new one :)
                 */
                try {

                    directoryHandler.delete(new File(directory));
                } catch (IOException e) {
                    System.out.println("Failed due to unknown reason :( ");
                    e.printStackTrace();
                }
                new File(directory).mkdirs(); // Make new directory
            }
        }
        return new File(directory).getAbsolutePath(); // it is required to get the absolute path
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        query = stringQuery.Optimization(query);
        syntaxChecker = new SyntaxChecker();
        boolean valid = false;
        if (syntaxChecker.createDatabase(query)) {
            createDatabase(informations.getDatabaseName(), false);
        } else if (syntaxChecker.createTable(query)) {
            return createTable(informations.getDatabaseName(), informations.getTableName());
        } else if (syntaxChecker.dropDatabase(query)) {
            createDatabase(informations.getDatabaseName(), true);
        } else if (syntaxChecker.dropTable(query)) {
            directoryHandler.deleteFile(informations.getDatabaseName(), informations.getTableName());
        }else{
            throw new SQLException("Error in creating or dropping a database or a table");
        }
        return true;
    }
    @Override
    public Object[][] executeQuery(String query) throws SQLException {
        query = stringQuery.Optimization(query);
        Print print = new Print();
        String[] selectColumnsName;
        String[] columnsName;

        if (syntaxChecker.select(query)) {
            selectColumnsName = informations.getSelectProperties();
            columnsName = xml.getColumnsName(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));
            String path = directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName(),"xml"); // 3shan t3rf eza kan el path b null 2w la
            if (!found(columnsName,selectColumnsName) || path == null) throw new SQLException("error in the columns name");

            Object[][] arr = storage.load(path);
            Object[][] res = select.select(arr, columnsName, informations.getSelectProperties());

            return res;

        } else if (syntaxChecker.selectAll(query)) {

            String path = directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName(),"xml"); // 3shan t3rf eza kan el path b null 2w la
            if (path == null) throw new SQLException("error in the columns name");

            columnsName = xml.getColumnsName(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));
            Object[][] arr = storage.load(path);
            Object[][] res = select.selectAll(arr, columnsName);

            return res ;

        }else if (syntaxChecker.selectWithCondition(query) || syntaxChecker.selectAllWithCondition(query)){
            String path = directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName(),"xml");
            if (path == null) throw new SQLException("error in the columns name");
            columnsName = xml.getColumnsName(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));
            selectColumnsName = informations.getSelectProperties();
            String column = informations.getCondition()[0];
            String value = informations.getCondition()[1];
            String operator = informations.getCondition()[2];
            Object[][] arr = storage.load(path);

            if (!search(columnsName,column)) throw new SQLException("error in the columns name");

            if(syntaxChecker.selectWithCondition(query)){

                if (!found(columnsName,selectColumnsName)) throw new SQLException("error in the columns name");
                Object[][] res = select.selectWhere(arr , columnsName , selectColumnsName , column , operator , value);

                return res;
            }else if(syntaxChecker.selectAllWithCondition(query)){
                Object[][] res = select.selectAllWhere(arr , columnsName  , column , operator , value);
                return res ;
            }
        }
        throw new SQLException("Error in the syntax of the select method ");
    }
    @Override
    public int executeUpdateQuery(String query) throws SQLException {

        query = stringQuery.Optimization(query);
        int count = -1;

        if ((syntaxChecker.insert(query) || syntaxChecker.insertWithoutColumnsName(query)) && checkOnUpdate()) {
            count = implementUpdateQuery.insert();

        } else if (syntaxChecker.delete(query) && checkOnUpdate()) {

            count = implementUpdateQuery.delete();

        } else if (syntaxChecker.update(query) && checkOnUpdate()) {
            count = implementUpdateQuery.update();

        } else if (syntaxChecker.deleteAll(query) && checkOnUpdate()) {
            count = implementUpdateQuery.deleteAll();

        } else if (syntaxChecker.updateWithoutCondition(query) && checkOnUpdate()) {
            count = implementUpdateQuery.updateAll();

        }

        if (count == -1) throw new SQLException("SQL update syntax error");
        else return count;
    }

    public boolean createTable(String databaseName, String tableName) throws SQLException {

        boolean valid = true;
        String databaseFound = directoryHandler.containFolder(databaseName);
        File tablePath = new File("Databases"+fileSeparator+databaseName+fileSeparator+tableName+".xml" );
        if (databaseFound == null) {
            throw new SQLException("Error the database is not found ");
        } else if (tablePath.exists()){
            valid = false;
        }else {
            String[] cn = informations.getCreateTablePropertiesName();
            String[] dn = informations.getCreateTablePropertiesDatatype();

            new Storage.Builder()
                    .databaseName(databaseName)
                    .tableName(tableName)
                    .columnNames(cn)
                    .dataTypes(dn)
                    .content(new Object[0][informations.getCreateTablePropertiesDatatype().length])
                    .build();
        }
        return valid;
    }
    // check on the columns names and the path
    private boolean found(String[] colunmsName,String[] columnsNameSelect){

        for (int i = 0; i < columnsNameSelect.length; i++) {
            if (!search(colunmsName,columnsNameSelect[i])) return false;
        }
        return true;
    }

    private boolean search(String[] Array,String target){

        for (int i = 0; i < Array.length; i++) {

            if (target.compareTo(Array[i]) == 0) return true;

        }
        return false;
    }

    private boolean checkOnUpdate(){
        String path = directoryHandler.containFile(informations.getDatabaseName(), informations.getTableName(), "xml");
        if (path != null)
        {
            implementUpdateQuery = new ImplementUpdateQuery(path);
            return true;
        }
        return false;
    }

}

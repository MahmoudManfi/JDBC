package eg.edu.alexu.csd.oop.db.cs58;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Informations {

    private String tableName; // to store the table name
    private String[] createTablePropertiesName; // to store the columns name
    private String[] createTablePropertiesDatatype; // to store for each column their datatype
    private String databaseName; // to store the database name
    private String[] insertIntoPropertiesName; // store the name column of new row the user enter
    private String[] insertIntoPropertiesValues; // the values in the new row stored as same as the name take your care
    private String[] condition; // store the whole condition as condition[0] the name of column, condition[1] is the value and condition[2] is the operation
    private String[] updatePropertiesName; // store data of the update the name of the columns
    private String[] updatePropertiesValue; // store data of the update the value of the columns
    private String[] selectProperties; // store the name of the selected table
    private Informations(){}

    private static Informations informations;

    public static Informations getInstance() {

        if(informations == null) {

            informations=new Informations();

        }
        return informations;
    }

    public void setCreateTableProperties (String properties) {

        String[][] result = spliter(' ',properties);
        createTablePropertiesName = result[0];
        createTablePropertiesDatatype = result[1];
    }

    public String getTableName() {
        return tableName;
    }

    public String[] getCreateTablePropertiesDatatype() { return createTablePropertiesDatatype; }

    public String[] getCreateTablePropertiesName() { return createTablePropertiesName; }

    public String[] getInsertIntoPropertiesName() {
        return insertIntoPropertiesName;
    }

    public String[] getInsertIntoPropertiesValues() {
        return insertIntoPropertiesValues;
    }

    public String[] getUpdatePropertiesName() {
        return updatePropertiesName;
    }

    public String[] getUpdatePropertiesValue(){return updatePropertiesValue; }

    public String getDatabaseName() {
        return databaseName;
    }

    public String[] getSelectProperties() { return selectProperties; }

    public String[] getCondition() {
        return condition;
    }

    public void setCondition(String string) {

        Pattern pattern = Pattern.compile("^([a-zA-Z]\\w*)([=<>])((?:[0-9]+)|(?:'[^\\n]+'))$");
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        condition = new String[3];
        condition[0]=matcher.group(1); condition[1]=matcher.group(3).replaceAll("'",""); condition[2]=matcher.group(2);

    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setSelectProperties(String properties) {

        selectProperties = properties.split(",");

    }

    public void setUpdateProperties(String properties) {

        String[][] result = spliter('=',properties);
        updatePropertiesName = result[0];
        updatePropertiesValue = result[1];

    }

    public void setInsertIntoProperties(String name, String values) {

        insertIntoPropertiesValues = values.split(",");

        if (name != null) {

            insertIntoPropertiesName = name.split(",");

        } else insertIntoPropertiesName = null;
    }

    private String[][] spliter(char character,String string){
        String[][] result = new String[2][];
        String[] split = string.split(",");
        result[0] = new String[split.length];
        result[1] = new String[split.length];
        for (int i = 0; i < split.length; i++) {

            String[] array = split[i].split(character+"");
            result[0][i] = array[0];
            result[1][i] = array[1];

        }
        return result;
    }

    public void setAllNull(){

        tableName = null; createTablePropertiesDatatype=null; createTablePropertiesName=null;
        selectProperties = null; insertIntoPropertiesName = null; insertIntoPropertiesValues = null;
        condition = null; updatePropertiesValue = null; updatePropertiesName = null;

    }

}

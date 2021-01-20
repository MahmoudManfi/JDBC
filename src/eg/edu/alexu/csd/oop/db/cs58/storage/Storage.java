package eg.edu.alexu.csd.oop.db.cs58.storage;

public class Storage {
    private String databaseName;
    private String tableName;
    private String[]dataTypes;
    private String[]columnNames;
    private Object[][]content;

    private XML xml = new XML();
    private XSD xsd = new XSD();

    public void store(Builder builder) {
        this.databaseName = builder.databaseName;
        this.tableName = builder.tableName;
        this.dataTypes = builder.dataTypes;
        this.columnNames = builder.columnNames;
        this.content = builder.content;

        String fileSeparator = System.getProperty("file.separator");
        xml.Write(databaseName, tableName , content , columnNames , dataTypes);
        xsd.createValidationFile(tableName ,"Databases"+fileSeparator+databaseName, columnNames , dataTypes);
    }

    public Object[][] load(String path)
    {
        return xml.Read(path);
    }
    public boolean validate(String databaseName , String tableName)
    {
        String xmlPath = databaseName + System.getProperty("file separator")+ tableName + ".xml";
        String xsdPath = databaseName + System.getProperty("file separator")+ tableName + ".xsd";
        return xsd.isValidTable(xmlPath ,xsdPath );
    }


    public static class Builder {
        private String databaseName = null;
        private String tableName = null;
        private String[]dataTypes = null;
        private String[]columnNames = null;
        private Object[][]content = null;

        public Builder(){

        }
        public Builder tableName(String tableName){
            this.tableName =  tableName;
            return this;
        }
        public Builder databaseName(String databaseName){
            this.databaseName =  databaseName;
            return this;
        }
        public Builder columnNames(String[] columnNames){
            this.columnNames =  columnNames;
            return this;
        }
        public Builder dataTypes(String[] dataTypes){
            this.dataTypes =  dataTypes;
            return this;
        }
        public Builder content(Object[][] content){
            this.content =  content;
            return this;
        }
        public void build(){
             new Storage().store(this);
        }
    }

    //Test
    public static void main(String[] args) {
        String fileSeparator = System.getProperty("file.separator");
        Object[][] arr = new Object[][]{{"Hamza", "Hassan",26}, {"Hamza", "Hassan",26}, {"Hamza", "Hassan",26}};
        String[] cn = new String[]{"Name", "Last", "Age"};
        String[] dn = new String[]{"string", "string", "integer"};
        String tableName = "testBuilder";

        new Storage.Builder()
               .databaseName("Lolo")
               .tableName(tableName)
               .columnNames(cn)
               .dataTypes(dn)
               .content(arr)
               .build();


    }




}



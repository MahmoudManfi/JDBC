package eg.edu.alexu.csd.oop.db.cs58.storage;

public interface XsdValidation {
    /**
     *
     * @param tableName table name
     * @param path the path that the xsd file will be saved at
     * @param columns the column name of the table
     * @param datatypes the data type for each columns
     */
    public void createValidationFile(String tableName , String path , String[] columns , String[] datatypes );


    /**
     *
     * @param xmlFile path of the xml file
     * @param validationFile path of the validation file of the xml file
     * @return
     */
    public boolean isValidTable(String xmlFile , String validationFile);
}

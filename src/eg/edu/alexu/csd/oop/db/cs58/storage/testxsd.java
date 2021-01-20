package eg.edu.alexu.csd.oop.db.cs58.storage;

import eg.edu.alexu.csd.oop.db.cs58.DirectoryHandler;

public class testxsd {

    public static void main(String[] args){
        String fileSeparator = System.getProperty("file.separator");
        XsdValidation xsd = new XSD();
        XML xml = new XML();
        DirectoryHandler directoryHandler = new DirectoryHandler();
        Object[][] arr = new Object[][]{{"Hamza", "Hassan",26}, {"Hamza", "Hassan",26}, {"Hamza", "Hassan",26}};
        String[] cn = new String[]{"Name", "Last", "Age"};
        String[] dn = new String[]{"string", "string", "integer"};
        String tableName = "TestTable1";
        xml.Write("Base", tableName, arr, cn, dn);
        String path = "Databases" + fileSeparator + "Base";
        xsd.createValidationFile(tableName, path, cn, dn);
        String xsdPath = directoryHandler.containFile("Base", tableName, "xsd");
        String xmlPath = directoryHandler.containFile("Base", tableName, "xml");
        System.out.println(xsd.isValidTable( xmlPath,xsdPath));
        System.out.println("Try the false validation now  ");
        Object[][] arr2 = new Object[][]{{"Hamza", "Lol", "Fuc*"}, {"Hamza", "Hassan",26}, {"Hamza", "Hassan",26}};
        xml.Write("Base", tableName, arr2, cn, dn);
        xmlPath = directoryHandler.containFile("Base", tableName, "xml");
        System.out.println(xsd.isValidTable(xmlPath, xsdPath));



    }
}

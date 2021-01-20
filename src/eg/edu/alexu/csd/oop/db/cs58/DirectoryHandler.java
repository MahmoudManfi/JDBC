//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package eg.edu.alexu.csd.oop.db.cs58;

import java.io.File;
import java.io.IOException;

public class DirectoryHandler {
    String fileSeparator = System.getProperty("file.separator");

    public DirectoryHandler() {
    }

    public String makeFolder(String FolderName) {
        String directory = "Databases" + this.fileSeparator + FolderName;
        (new File(directory)).mkdir();
        String path = null;
        return (String) path;
    }
    public String searchForDirectory(String directoryName) {
        String path = null;
        return (String) path;
    }

    public void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());
            } else {
                String[] files = file.list();
                String[] var3 = files;
                int var4 = files.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    String temp = var3[var5];
                    File fileDelete = new File(file, temp);
                    this.delete(fileDelete); // recursion
                }

                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }
        } else {
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }

    }

    /**
     * @param name the name of the database that we want to check whether it exists or not
     * @return the database absolute path if it exists else we return null
     */
    public String containFolder(String name) {
        String res = null ;
        String directory = "Databases" +fileSeparator+  name;
        File file=  new File(directory);
        if(file.exists()){
            return file.getAbsolutePath();
        }else {
            return null ;
        }

    }
//    public String containFolder(String name) {
//        File file = new File("Databases");
//        String res = null;
//        if (file.isDirectory()) {
//            String[] databasesNames = file.list();
//            for (int i = 0; i < databasesNames.length; i++) {
//                if (databasesNames[i].equalsIgnoreCase(name)) {
//                    res = new File("Databases" + fileSeparator + name).getAbsolutePath();
//                    break;
//                }
//            }
//        } else {
//            System.out.println("Unexpexted Error ocurred from contain folder method in Directory handler ");
//        }
//        return res;
//
//    }

    /**
     * @param databaseName the name of the database that we expect the file to be inside
     * @param tableName    the name of the table file
     * @param extension    the extension of the file (ie ".xml" or ".xsd" )
     *                     you can neglect the '.' and the method adds it automatically
     * @return the path of the file or null
     */
    public String containFile(String databaseName, String tableName, String extension) {
        extension = extension.toLowerCase();
        if (extension.charAt(0) != '.')
            extension = '.' + extension;
        if (containFolder(databaseName) != null) {
            File file = new File("Databases" + fileSeparator + databaseName + fileSeparator + tableName + extension);
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * @param folder   the name of the database
     * @param fileName the name of the table
     * @return true if it is successfully deleted and false otherwise
     */
    public boolean deleteFile(String folder, String fileName) {
        if (containFile(folder, fileName, ".xsd") == null ||
                containFile(folder, fileName, ".xml") == null) {
            return false;
        }
        File xsdFile = new File(folder + fileSeparator + fileName);
        File xmlFile = new File(folder + fileSeparator + fileName);
        xmlFile.delete();
        xsdFile.delete();
        return true;
    }

}


package eg.edu.alexu.csd.oop.db.cs58.storage;

import java.io.File;

public class CreateNewFile {
    public File fileCreated(String dataBase, String table){
        String fileSeparator = System.getProperty("file.separator");
        try {
            String directory = "Databases"+fileSeparator+dataBase;
            File file = new File(directory);
            file.mkdir();
            String path = directory+fileSeparator+table+".xml";
            file = new File(path);
            file.createNewFile();
            return file;
        }catch(Exception e) {
            System.out.println("Error when Creating new File");
            //e.printStackTrace();
            return null;
        }
    }
}

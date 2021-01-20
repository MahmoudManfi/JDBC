package eg.edu.alexu.csd.oop.db.cs58;
import eg.edu.alexu.csd.oop.db.cs58.storage.XML;
import java.util.LinkedList;
import java.util.Queue;


public class Select {

    private XML xml = new XML();

    private Compare compare = new Compare();
    private Informations informations = Informations.getInstance();
    private DirectoryHandler directoryHandler = new DirectoryHandler();

    public Object[][] selectAll (Object Array[][], String columnNames[]){

        String[] dataType = xml.getDataType(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));

        Object[][] printable = new Object[Array.length+1][columnNames.length];
        for(int i = 0; i<columnNames.length; i++){
            printable[0][i]=columnNames[i];
        }
        for(int i = 1; i<printable.length; i++){
            for(int j=0; j<printable[i].length;j++){
                if (dataType[j].compareTo("varchar") == 0) {
                    printable[i][j]=Array[i-1][j];
                } else{

                    printable[i][j] = Integer.parseInt((String) Array[i-1][j]);

                }

            }
        }
        return printable;
    }
    public Object[][] selectAllWhere (Object Array[][], String columnNames[], String Column, String Operator, String Condition){

        String[] dataType = xml.getDataType(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));

        int selected =0;
        Queue indexes = new LinkedList();
        for(selected = 0; selected<columnNames.length; selected++){
            if(Column.equals(columnNames[selected])) {
                break;
            }
        }
        if (dataType[selected].compareTo("int") == 0)
        for(int i=0; i<Array.length; i++){

            if (compare.twoStrings(Integer.parseInt((String) Array[i][selected]),Integer.parseInt(Condition),Operator))
            {
                indexes.add(i);
            }
        }
        else {
            for(int i=0; i<Array.length; i++){

                if (compare.twoStrings(Array[i][selected],Condition,Operator))
                {
                    indexes.add(i);
                }
            }
        }

            Object[][] printable = new Object[indexes.size()+1][Array[0].length];
            for(int i = 0; i<columnNames.length; i++){
                printable[0][i]=columnNames[i];
            }
            int k=1;
            while (!(indexes.isEmpty())){
                int i = (int)indexes.remove();
                for(int j=0; j<Array[i].length;j++){

                    if (dataType[j].compareTo("varchar") == 0) {
                        printable[k][j]=Array[i][j];
                    } else{
                        printable[k][j] = Integer.parseInt((String) Array[i][j]);
                    }

                }
                k++;
            }
            return printable;
    }

    public Object[][] select(Object Array[][], String columnNames[], String columnsNeeded[]){

        String[] dataType = xml.getDataType(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));

        Queue columnIndexes = new LinkedList();
        for(int i =  0; i<columnNames.length; i++){
            for(int j= 0; j<columnsNeeded.length; j++){
                if(columnNames[i].equals(columnsNeeded[j])){
                    columnIndexes.add(i);
                }
            }
        }
        Object[][] printable = new Object[Array.length+1][columnIndexes.size()];
        Queue tempColumns = new LinkedList(columnIndexes);
        int y=0;
        while(!(columnIndexes.isEmpty())){
            int i = (int) columnIndexes.remove();
            printable[0][y]=columnNames[i];
            y++;
        }
        y=0;
        int x=1;
        for(int row = 0; row<Array.length; row++){
            Queue columns = new LinkedList(tempColumns);
            while (!(columns.isEmpty())){
                int col = (int) columns.remove();

                if (dataType[col].compareTo("varchar") == 0) {

                    printable[x][y]=Array[row][col];

                } else {

                    printable[x][y]=Integer.parseInt((String) Array[row][col]);

                }

                y++;

            }
            y=0;
            x++;
        }
        return printable;
    }

    public Object[][] selectWhere(Object Array[][], String columnNames[], String columnsNeeded[], String Column, String Operator, String Condition){

        String[] dataType = xml.getDataType(directoryHandler.containFile(informations.getDatabaseName(),informations.getTableName()+"-DT","xml"));

        Queue columnIndexes = new LinkedList();
        for(int i =  0; i<columnNames.length; i++){
            for(int j= 0; j<columnsNeeded.length; j++){
                if(columnNames[i].equals(columnsNeeded[j])){
                    columnIndexes.add(i);
                }
            }
        }
        int selected =0;
        Queue indexes = new LinkedList();
        for(selected = 0; selected<columnNames.length; selected++){
            if(Column.equals(columnNames[selected])) {
                break;
            }
        }
                if (dataType[selected].compareTo("int") == 0)
                    for(int i=0; i<Array.length; i++){

                        if (compare.twoStrings(Integer.parseInt((String) Array[i][selected]),Integer.parseInt(Condition),Operator))
                        {
                            indexes.add(i);
                        }
                    }
                else {
                    for(int i=0; i<Array.length; i++){

                        if (compare.twoStrings(Array[i][selected],Condition,Operator))
                        {
                            indexes.add(i);
                        }
                    }
                }

            Object[][] printable = new Object[indexes.size()+1][columnIndexes.size()];
            Queue tempColumns = new LinkedList(columnIndexes);
            int y=0;
            while(!(columnIndexes.isEmpty())){
                int i = (int) columnIndexes.remove();
                printable[0][y]=columnNames[i];
                y++;
            }
            y=0;
            int x = 1;
            while (!(indexes.isEmpty())){
                int row =  (int) indexes.remove();
                Queue columns = new LinkedList(tempColumns);
                while (!(columns.isEmpty())){
                    int col = (int) columns.remove();

                    if (dataType[col].compareTo("varchar") == 0) {

                        printable[x][y]=Array[row][col];

                    } else {

                        printable[x][y]=Integer.parseInt((String) Array[row][col]);

                    }
                    y++;
                }
                y=0;
                x++;
            }
            return printable;
    }
}

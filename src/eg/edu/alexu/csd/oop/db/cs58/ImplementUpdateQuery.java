package eg.edu.alexu.csd.oop.db.cs58;

import eg.edu.alexu.csd.oop.db.cs58.storage.XML;

import java.util.ArrayList;
import java.util.Arrays;

public class ImplementUpdateQuery {

    private Informations informations;
    private String tableName;
    private String[][] table;
    private DirectoryHandler directoryHandler;
    private XML xml;
    private String[] tableColumns;
    private String[] tableDatatype;
    private String path1;
    private String[] condition;
    private Compare compare;

    ImplementUpdateQuery(String path) {
        xml = new XML();
        directoryHandler = new DirectoryHandler();
        informations = Informations.getInstance();
        tableName = informations.getTableName();
        path1 = directoryHandler.containFile(informations.getDatabaseName(),tableName+"-DT","xml");


        tableColumns = xml.getColumnsName(path1);
        tableDatatype = xml.getDataType(path1);
        condition = informations.getCondition();
        Object[][] temp = xml.Read(path);
        table = new String[temp.length][temp.length];
        for (int i = 0; i < temp.length; i++) {
            table[i] = Arrays.stream(temp[i]).toArray(String[]::new);
        }
        compare = new Compare();
    }

    public int insert() {
        String[] names;
        if (informations.getInsertIntoPropertiesName() == null) {

            names = tableColumns;

        } else names = informations.getInsertIntoPropertiesName();
        String[] values = informations.getInsertIntoPropertiesValues();

        if (names.length != values.length) return -1;

        String[][] temp = sort(names,values);
        names = temp[0];
        values = temp[1];

        if (equals(names,tableColumns) && isValidData(tableDatatype,values)) {

            String[][] newTable = getNewTable(table,values);
            write(newTable);
            return 1;

        }
        return -1;
    }

    public int deleteAll(){

        int count = table.length;
        table = new String[0][tableColumns.length];
        write(table);
        return count;

    }

    public int delete() {

        int count =getColumnNamber(condition[0]);
        if(count==-1) return -1;
        ArrayList<Integer> numbers = new ArrayList<>();
        if (tableDatatype[count].compareTo("int") == 0){
            for (int i = 0; i < table.length; i++) {

                if (!compare.twoStrings(Integer.parseInt(table[i][count]),Integer.parseInt(condition[1]),condition[2])){

                    numbers.add(i);

                }

            }
        } else {

            for (int i = 0; i < table.length; i++) {

                if (!compare.twoStrings(table[i][count],condition[1],condition[2])){

                    numbers.add(i);

                }

            }

        }

        count = table.length-numbers.size();

        String[][] newTable = new String[numbers.size()][tableColumns.length];
        for (int i = 0; i < numbers.size(); i++) {

            newTable[i] = table[numbers.get(i)];

        }

        write(newTable);

        return count;
    }

    public int update() {

        String[] name = informations.getUpdatePropertiesName();
        String[] value = informations.getUpdatePropertiesValue();
        ArrayList<Integer> numbers = new ArrayList<>();

        String[] temp = forUpdate(name,value,numbers);

        if (temp == null) return -1;
        value = temp;

        int count = getColumnNamber(condition[0]);
        if (count == -1) return -1;

        int counter =0;
        if (tableDatatype[count].compareTo("int") == 0) {

            for (int i = 0; i < table.length; i++) {

                if (compare.twoStrings(Integer.parseInt(table[i][count]),Integer.parseInt(condition[1]),condition[2])) {
                    for (int j = 0; j < numbers.size(); j++) {

                        table[i][numbers.get(j)]=value[j].replaceAll("'","");

                    }
                    counter++;
                }
            }

        } else {

            for (int i = 0; i < table.length; i++) {

                if (compare.twoStrings(table[i][count],condition[1],condition[2])) {
                    for (int j = 0; j < numbers.size(); j++) {

                        table[i][numbers.get(j)]=value[j].replaceAll("'","");

                    }
                    counter++;
                }
            }

        }

        write(table);
        return counter;
    }

    public int updateAll(){

        String[] name = informations.getUpdatePropertiesName();
        String[] value = informations.getUpdatePropertiesValue();
        ArrayList<Integer> numbers = new ArrayList<>();

        String[] temp = forUpdate(name,value,numbers);

        if (temp == null) return -1;
        value = temp;
        for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < numbers.size(); j++) {
                    table[i][numbers.get(j)]=value[j].replaceAll("'","");
            }
        }
        write(table);

        return table.length;
    }

    private boolean equals(String[] arr1, String[] arr2) {

        boolean flag = true;

        for (int i = 0; i < arr1.length; i++) {

            if (arr1[i].compareTo(arr2[i])!= 0) {
                flag = false; break;
            }

        }

        return flag;
    }

    private boolean isValidData(String[] datatype, String[] values) {

        for (int i = 0; i < datatype.length; i++) {

            if (datatype[i].compareTo("varchar") ==0 && values[i].charAt(0) != '\'') {
                return false;
            }else if (datatype[i].compareTo("int") == 0 && values[i].charAt(0) == '\''){
                return false;
            }

        }

        return true;
    }

    private String[][] getNewTable(String[][] oldTable, String[] option ) {

        String[][] NewTable = new String[oldTable.length+1][oldTable.length];
        for (int i = 0; i < oldTable.length; i++) {

            NewTable[i] = oldTable[i];

        }

        for (int i = 0; i < option.length; i++) {

          option[i]   = option[i].replaceAll("'","");

        }

        NewTable[oldTable.length] = option;
            return NewTable;
    }

    private void write(String[][] table){

        xml.Write(informations.getDatabaseName(),tableName,table,tableColumns,tableDatatype);

    }

    private int getColumnNamber(String column){

        for (int i = 0; i < tableColumns.length; i++) {

            if (column.compareTo(tableColumns[i])==0) return i;

        }
        return -1;
    }

    private String[][] sort(String[] arr1, String[] arr2){

        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        for (int i = 0; i < tableColumns.length; i++) {

                int count = search(arr1,tableColumns[i]);
                if (count!= -1) {

                    temp1.add(arr1[count]);
                    temp2.add(arr2[count]);
                }

        }
        arr1 = Arrays.stream(temp1.toArray()).toArray(String[]::new);
        arr2 = Arrays.stream(temp2.toArray()).toArray(String[]::new);
        String[][] returned = new String[2][];
        returned[0] = arr1;
        returned[1] = arr2;
        return returned;
    }

    private int search(String[] Array,String target){


        for (int i = 0; i < Array.length; i++) {

            if (target.compareTo(Array[i]) == 0) return i;

        }

        return -1;

    }

    private String[] forUpdate(String[] name,String[] value, ArrayList<Integer> numbers){

        String[][] temp1 = sort(name,value);

        for (int i = 0; i < temp1[0].length; i++) {

            int temp = getColumnNamber(temp1[0][i]);
            if (temp == -1) return null;
            numbers.add(temp);
        }
    return temp1[1];
    }

}

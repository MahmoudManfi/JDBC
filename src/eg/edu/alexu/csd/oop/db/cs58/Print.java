package eg.edu.alexu.csd.oop.db.cs58;

import java.util.LinkedList;
import java.util.Queue;

public class Print {

    public Object[][] removeColumns(Object[][]printable){
        Object[][] finalArray = new Object [printable.length-1][printable[0].length];
        for(int i =0; i<finalArray.length; i++){
            finalArray[i]=printable[i+1];
        }
        return finalArray;
    }

    public Object[][] print(Object[][] Data){
        for(int i = 0; i<Data.length; i++){
            for(int j=0; j<Data[i].length;j++){
                if(Data[i][j]==null){
                    continue;
                }
                System.out.print(Data[i][j]);
                if(j==Data[i].length-1){
                    System.out.println();
                }else{
                    System.out.print("\t");
                }
            }
        }
        return removeColumns(Data);
    }
}

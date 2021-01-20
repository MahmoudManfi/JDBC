package eg.edu.alexu.csd.oop.db.cs58;

import java.util.Stack;

public class Compare {

    public boolean twoStrings(Object first, Object second, String operator){

        int num;

        try{

            num = (Integer) first - (Integer) second;

        } catch (Exception e){

            num = ((String)first).compareTo((String)second);

        }

        switch (operator){
            case ">" : return num>0;
            case "<" : return num<0;
            case "=" : return num==0;
        }
        return false;
    }


}

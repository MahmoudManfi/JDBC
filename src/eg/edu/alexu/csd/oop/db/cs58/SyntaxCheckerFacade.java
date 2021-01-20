package eg.edu.alexu.csd.oop.db.cs58;

import java.sql.SQLException;

public class SyntaxCheckerFacade {
    SyntaxChecker syntaxChecker = new SyntaxChecker();

    public boolean check(String query) throws SQLException {
        query = query.trim().replaceAll("\\s+", " ");
        query = simplificationForString(query);
        boolean valid = false;
        boolean containTable = query.contains("table");
        boolean containDatabase = query.contains("database");
        boolean containDrop = query.contains("drop");
        boolean containCreate = query.contains("create");
        boolean containSelect = query.contains("select");
        boolean containSelectAll = query.contains("select *");
        boolean containCondition = query.contains("where");
        boolean containUpdate = query.contains("update");
        boolean containInsert = query.contains("insert");
        if (containCreate) {
            if (containTable) {
                valid = syntaxChecker.createTable(query);
            } else if (containDatabase) {
                valid =  syntaxChecker.createDatabase(query);
            }
        } else if (containDrop) {
            if (containTable) {
                valid = syntaxChecker.dropTable(query);

            } else if (containDatabase) {
                valid = syntaxChecker.dropDatabase(query);
            }
        } else if (containUpdate) {
            valid = syntaxChecker.update(query);
        } else if (containInsert) {
            valid = syntaxChecker.insert(query);
        }

        if (!valid) {
            throw new SQLException();
        }
        return valid;
    }



    private String simplificationForString(String query) {

        for (int i = 0; i < query.length(); i++) {

            char c = query.charAt(i);

            if (query.charAt(i) != '\'') {
                c = Character.toLowerCase(query.charAt(i));
                query = query.substring(0, i) + c + query.substring(i + 1, query.length());
            }
            else {
                i++;
                while (i<query.length() && query.charAt(i) !='\'') i++;
            }
            if (i<query.length())
                if (query.charAt(i) == ',' || query.charAt(i) == '=' || query.charAt(i) == '>' || query.charAt(i) == '<' || query.charAt(i) == ')' || query.charAt(i) == '(') {

                    if (i-1>=0 && query.charAt(i-1) == ' ') {
                        query = query.substring(0,i-1) + query.substring(i,query.length()); i--;
                    }
                    if (i+1<query.length() && query.charAt(i+1) == ' ') {
                        query = query.substring(0,i+1) + query.substring(i+2,query.length());i--;
                    }

                }
        }
        return query;
    }
}

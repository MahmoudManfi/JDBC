package eg.edu.alexu.csd.oop.db.cs58;

import java.sql.SQLException;

public class Parser {

    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private Informations informations = Informations.getInstance();

    public void gotoDatabase(String query) {

        if (query.contains("create") || query.contains("drop") || query.contains("table") || query.contains("database")) {

            try {
                boolean AllTrue = databaseManager.executeStructureQuery(query);
                //System.out.println(AllTrue);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (query.contains("select") || query.contains("*")) {
            try {
                databaseManager.executeQuery(query);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (query.contains("delete") || query.contains("where") || query.contains("update") || query.contains("set") || query.contains("insert") || query.contains("into") || query.contains("values")) {
            try {
                int num = databaseManager.executeUpdateQuery(query);
                System.out.println("number of updates is " + num + " in the \"" + query + "\"query");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else
            System.out.println("syntax error");

        informations.setAllNull();
    }

}

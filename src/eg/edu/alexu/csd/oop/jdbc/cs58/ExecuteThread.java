package eg.edu.alexu.csd.oop.jdbc.cs58;

import eg.edu.alexu.csd.oop.db.Database;

import java.sql.SQLException;

public class ExecuteThread extends Thread{

    private Object value;
    private Database database;
    private String query;

    public ExecuteThread(Database database,String query) {

        this.database = database;
        this.query = query.toLowerCase();

    }

    public Object getValue (){
        return value;
    }

    @Override
    public void run() {

        try {

            if (query.contains("create") || query.contains("drop") && (query.contains("table") || query.contains("database"))) {
                value = database.executeStructureQuery(query);
            } else if (query.contains("select") && query.contains("from")) {
                 value = database.executeQuery(query);
            } else {
                value = database.executeUpdateQuery(query);
            }

        } catch (SQLException e) {

            value = e.getMessage();

        }

    }
}

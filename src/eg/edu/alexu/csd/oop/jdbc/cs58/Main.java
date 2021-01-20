package eg.edu.alexu.csd.oop.jdbc.cs58;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main{

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws SQLException, InterruptedException {

        Driver driver  = DriverManager.getInstance();
        Properties info = new Properties();
        File dbDir = new File("Databases");
        info.put("path", dbDir.getAbsoluteFile());
        Connection connection;
        Statement statement;
        Scanner in = new Scanner(System.in);

        connection = driver.connect("jdbc:xmldb://localhost", info);
        statement = connection.createStatement();


        String query;

        while (true){

            System.out.println("Enter sql query :)");
            query = in.nextLine().trim().toLowerCase();
            try {
                if ((query.contains("create") || query.contains("drop")) && (query.contains("table") || query.contains("database")))
                    statement.execute(query);
                else if (query.contains("select") && query.contains("from"))
                    statement.executeQuery(query);
                else
                    statement.executeUpdate(query);
            } catch (SQLException e) {
                
                LOGGER.log(Level.SEVERE,e.getMessage());

            }
            TimeUnit.MICROSECONDS.sleep(100);
        }


    }


}

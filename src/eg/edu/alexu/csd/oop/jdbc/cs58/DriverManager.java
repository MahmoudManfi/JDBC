package eg.edu.alexu.csd.oop.jdbc.cs58;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager implements java.sql.Driver {

    private static DriverManager driver;

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private DriverManager(){}

    public static DriverManager getInstance(){

        if (driver == null){
            driver=new DriverManager();
        }

        return driver;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {

        if (url == null) throw new SQLException("the url is null"); // throw when the url is null

        else return url.equals("jdbc:xmldb://localhost"); // this is our dpms to work on it

    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {

        if (info == null) throw new SQLException("info shouldn't be null");

        if (!acceptsURL(url)) return null; // if he didn't find the url in the connection return null

        LOGGER.log(Level.INFO,"The connection is Created");

        return new ConnectionManager(info.getProperty("path",""));
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {

        if (!acceptsURL(url)) throw new SQLException("information is false"); // if he didn't find the url in the connection throw the exception

        return new DriverPropertyInfo[0];
    }

    /**
     *                              stop here
     */

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }
}

package eg.edu.alexu.csd.oop.jdbc.cs58;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs58.Print;


import java.awt.print.Printable;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatementManager implements java.sql.Statement {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Connection connection; // this connection of this statement
    private Database database; // the database the connection have
    private List<String> list;
    private int seconds;
    private Thread timer;
    ExecuteThread executeThread;

    public StatementManager(Database database,Connection connection) {

        this.database = database;
        this.connection = connection;
        list = new LinkedList<>();
        seconds=0;
        timer = new Timer(seconds);

    }

    private void throwTime(Thread thread)  {
        timer = new Timer(seconds);
        timer.start();
        thread.start();

        while (thread.isAlive()) {
            if(timer.isInterrupted()) {
                thread.stop();
            }
        }

    }

    private void detectClose() throws SQLException {
        if (database == null) throw new SQLException("The statement is already closed");
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        detectClose();

        LOGGER.log(Level.INFO,"there is knew commands is added");
        list.add(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        detectClose();

        LOGGER.log(Level.INFO,"The batch list is cleared");
        list.clear();
    }

    @Override
    public void close() throws SQLException {
        detectClose();

        database = null; connection = null; list = null;

        LOGGER.log(Level.INFO,"The statement is closed");
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        detectClose();
        Object res;
        executeThread = new ExecuteThread(database,sql);
        throwTime(executeThread);

        if (timer.isInterrupted()) {
            throw new SQLTimeoutException("time limited exceeded");
        }
        res= executeThread.getValue();

        if (res instanceof String) throw new SQLException(res.toString());
        else if (res instanceof Object[][]){
            if (((Object[][]) res).length >1) res=true;
            else res = false;
        } else if (res instanceof Integer) {
            if ((Integer)res > 0) res = true;
            else res = false;
        }

        LOGGER.log(Level.INFO,"execute sql is done           " + (boolean)res);
        return (boolean)res;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        detectClose();

        Timer timer1 = new Timer(seconds);
        int[] arr = new int[list.size()]; int counter = 0;
        Object value;
        Iterator<String> iterator = list.iterator();
        timer1.start();
        while (iterator.hasNext()) {
            String query = iterator.next().toLowerCase();

            if (timer1.isInterrupted()) throw new SQLTimeoutException("time limited exceeded");

            try {

                execute(query);
                value = executeThread.getValue();

                if (value instanceof Integer) {

                    arr[counter++] = (int)value;

                } else arr[counter++] = Statement.SUCCESS_NO_INFO;

            } catch (SQLException e) {
                arr[counter++] = Statement.EXECUTE_FAILED;
            }
        }

        LOGGER.log(Level.INFO,"execute batch is done");
        return arr;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        detectClose();

        execute(sql);
        Object[][] table = (Object[][]) executeThread.getValue();
        ResultSet resultSet = new ResultSetManager(this,table);

        Print printable = new Print();
        System.out.println("table:");
        printable.print(table);
        LOGGER.log(Level.INFO,"resultSet is created");
        return resultSet;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        detectClose();

        execute(sql);
        int count = (int) executeThread.getValue();

        LOGGER.log(Level.INFO,"execute update is done            " + count);
        return count;
    }

    @Override
    public Connection getConnection() throws SQLException {
        detectClose();

        LOGGER.log(Level.INFO,"get connection is done");
        return connection;
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        detectClose();

        LOGGER.log(Level.INFO,"get query timeout is done");
        return seconds;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        detectClose();
        if (seconds < 0) throw new SQLException("the seconds should be positive or zero");

        LOGGER.log(Level.INFO,"set query timeout is done");
        this.seconds = seconds; timer = new Timer(seconds);
    }

    /**
     *  stop here
     */

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getUpdateCount() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void cancel() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }
}

package eg.edu.alexu.csd.oop.jdbc.cs58;

import eg.edu.alexu.csd.oop.db.Database;
import eg.edu.alexu.csd.oop.db.cs58.DatabaseManager;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager implements java.sql.Connection {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Database database;
    private String path; // law 3ndy method b7t feha el path

    public ConnectionManager(String path) {

        this.path = path;
        database = DatabaseManager.getInstance();

    }

    private void detectClose() throws SQLException {
        if (database == null) throw new SQLException("The connection is already closed");
    }

    @Override
    public void close() throws SQLException {
        detectClose();

        database = null;     path = null;

        LOGGER.log(Level.INFO,"The connection is closed");
    }

    @Override
    public Statement createStatement() throws SQLException {
        detectClose();

        LOGGER.log(Level.INFO,"The statement is created now");
        return new StatementManager(database,this);
    }

    /**
     *                                      stop here
     */

    @Override
    public StatementManager createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void commit() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void rollback() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }


    @Override
    public boolean isClosed() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getCatalog() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public StatementManager createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getSchema() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
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
}

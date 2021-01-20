package eg.edu.alexu.csd.oop.jdbc.cs58;
import eg.edu.alexu.csd.oop.db.cs58.Informations;

import java.sql.SQLException;
public class ResultSetMetaDataManager implements java.sql.ResultSetMetaData {
    private int columnCount;
    private String[] columnName;
    private int[] columnType;

    public ResultSetMetaDataManager(String[] columnName, int[] columnType , int columnCount) {
        this.columnCount = columnCount;
        this.columnCount = columnName.length;
        this.columnName = columnName;
        this.columnType = columnType;
    }
    private Informations informations ;

    @Override
    public int getColumnCount() throws SQLException {
        return columnCount;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return getColumnName(column);
    }
    @Override
    public String getColumnName(int column) throws SQLException {
        return columnName[column-1];
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        return columnType[column-1];
    }

    @Override
    public String getTableName(int column) throws SQLException {
        informations = Informations.getInstance();
        return informations.getTableName();

    }

    /**
     * ============================================================================
     *  stop here
     */

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
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

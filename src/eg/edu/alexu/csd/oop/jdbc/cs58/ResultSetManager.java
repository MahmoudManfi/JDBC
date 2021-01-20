package eg.edu.alexu.csd.oop.jdbc.cs58;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Logger;

public class ResultSetManager implements java.sql.ResultSet {

    private Statement statement;
    private String columnName[];
    private Object table[][];
    private int ptr; // point to the current row in the table
    private int rowsNumber;
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    /**
     * The error msg which will be thrown in any exception
     */
    private final String errorMsg = "Error : detected an operation upon a closed database";
    private int columnNumber;

    public ResultSetManager(Statement statement, Object[][] data) {
        this.statement = statement;
        ptr = -1;
        if (data != null) {
            columnName = new String[data[0].length];
            table = new Object[data.length - 1][data[0].length];
            initialize(data);
            rowsNumber = table.length;
            columnNumber = columnName.length;
        }

    }

    /**
     * @param data the 2d object array which consists of two parts
     *             1- the first row which has the names of the columns
     *             2- the rest of the array which has the table data
     */
    private void initialize(Object[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (i == 0) {
                    columnName[j] = data[i][j].toString();
                } else {
                    table[i - 1][j] = data[i][j];
                }
            }
        }
    }

    private boolean isEmptyDataset() {

        return table.length == 0;
    }

    private boolean isValidColumnIndex(int column) {

        return column > 1 && column <= columnNumber;
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        if (isClosed())
            throw new SQLException(errorMsg);

        boolean flag = false;
        if (row > 0 && row <= rowsNumber) {
            row--;
        } else if (row < 0 && row >= rowsNumber) {
            row = rowsNumber - row;
        } else {
              /*
                 in case of 0 or any invalid input the return value should be false
              */
            return false;
        }
        ptr = row;
        return true;
        //throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void afterLast() throws SQLException {
        if (isClosed())
            throw new SQLException(errorMsg);
        ptr = rowsNumber + 1;
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void beforeFirst() throws SQLException {
        if (isClosed())
            throw new SQLException(errorMsg);
        ptr = -1;
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void close() throws SQLException {
        table = null;
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        if (isClosed() || isEmptyDataset())
            throw new SQLException(errorMsg);
        for (int i = 0; i < columnName.length; i++) {
            if (columnLabel.equalsIgnoreCase(columnName[i])) {
                return (i + 1);
            }
        }
        throw new SQLException("Can not find the column !");

    }

    @Override
    public boolean first() throws SQLException {
        if (isClosed())
            throw new SQLException(errorMsg);
        else if (isEmptyDataset())
            return false;
        ptr = 0;
        return true;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        } else if (!isValidColumnIndex(columnIndex)) {
            throw new SQLException("Invalid column index ");
        } else {
            columnIndex--;
            try {
                int res = Integer.parseInt(table[ptr][columnIndex].toString());
                return res;
            } catch (Exception e) { // not mentionned in the docs
                return 0;
            }
        }
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        for (int i = 0; i < columnName.length; i++) {
            if (columnLabel.equalsIgnoreCase(columnName[i])) {
                return getInt(i + 1);
            }
        }
        // in case that we did not find the required name of column
        throw new SQLException("invalid columnname entry ");
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        if (isClosed() || isEmptyDataset()) {
            throw new SQLException(errorMsg);
        }
        int columnNumber = table[0].length;
        int[] dataTypes = new int[columnNumber];
        for (int j = 0; j < table[0].length; j++) {
            if (table[0][j] instanceof String)
                dataTypes[j] = Types.VARCHAR;
            else if (table[0][j] instanceof Integer)
                dataTypes[j] = Types.INTEGER;
        }
        ResultSetMetaData resultSetMetaData = new ResultSetMetaDataManager(columnName, dataTypes, columnNumber);
        return resultSetMetaData;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        } else if (!isValidColumnIndex(columnIndex)) {
            throw new SQLException("Invalid column index ");
        } else {
            columnIndex--;
            return table[ptr][columnIndex];
        }
    }

    @Override
    public Statement getStatement() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        return this.statement;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        } else if (!isValidColumnIndex(columnIndex)) {
            throw new SQLException("Invalid column index ");
        } else {
            columnIndex--;
            return table[ptr][columnIndex].toString();
        }
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        for (int i = 0; i < columnName.length; i++) {
            if (columnLabel.equalsIgnoreCase(columnName[i])) {
                return getString(i + 1);
            }
        }
        // in case that we did not find the required name of column
        throw new SQLException("invalid columnname entry ");
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        return !isEmptyDataset() && ptr == rowsNumber;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        return ptr == -1 && !isEmptyDataset();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return table == null;
    }

    @Override
    public boolean isFirst() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        return ptr == 0 && !isEmptyDataset();
    }

    @Override
    public boolean isLast() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        return !isEmptyDataset() && ptr + 1 == rowsNumber;

    }

    @Override
    public boolean last() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        } else if (isEmptyDataset())
            return false;
        else {
            ptr = rowsNumber - 1;
            return true;
        }
    }

    @Override
    public boolean next() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        }
        if (ptr == rowsNumber || isEmptyDataset()) {
            return false;
        }
        ptr++;
        if (ptr == rowsNumber)
            return false;
        return true;
    }

    @Override
    public boolean previous() throws SQLException {
        if (isClosed()) {
            throw new SQLException(errorMsg);
        } else if (ptr == -1 || isEmptyDataset())
            return false;
        ptr--;
        return true;
    }

    /**
     * ================================================================================================
     * ==============================1=====================1============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================11111111111111111111111=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ==============================1=====================1=============================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * ================================================================================================
     * <p>
     * stop here
     */
    @Override
    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }


    @Override
    public long getLong(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getType() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getConcurrency() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void insertRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Error unimplemented method");
    }


//    public static void main(String[] args) {
//        ResultSetManager resultSetManager = new ResultSetManager();
//    }
}

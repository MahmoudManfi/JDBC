package eg.edu.alexu.csd.oop.db.cs58;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * to make sure from the syntax of the query
 */

public class SyntaxChecker {

    private Pattern pattern;
    private Matcher matcher;
    private Informations informations = Informations.getInstance();

    public boolean createTable(String query) throws SQLException {
        pattern = Pattern.compile("^create\\stable\\s([a-zA-Z]\\w*)\\(((?:(?:(?:(?:[a-zA-Z]\\w*)\\s(?:varchar|int)),)*)(?:(?:[a-zA-Z]\\w*)\\s(?:varchar|int)))\\)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));
            informations.setCreateTableProperties(matcher.group(2));

            if (!CheckLength(informations.getCreateTablePropertiesName())) throw new SQLException("error the same column name");

            return true;

        }
        return false;
    }

    public boolean createDatabase(String query) {

        pattern = Pattern.compile("^create\\sdatabase\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setDatabaseName(matcher.group(1));

            return true;
        }

        return false;
    }

    public boolean dropTable(String query) {

        pattern = Pattern.compile("^drop\\stable\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));

            return true;
        }

        return false;
    }

    public boolean dropDatabase(String query) {

        pattern = Pattern.compile("^drop\\sdatabase\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setDatabaseName(matcher.group(1));

            return true;
        }

        return false;
    }

    public boolean select(String query) throws SQLException {

        pattern = Pattern.compile("^select\\s((?:(?:[a-zA-Z]\\w*,)*)(?:[a-zA-Z]\\w*))\\sfrom\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(2));
            informations.setSelectProperties(matcher.group(1));

            if (!CheckLength(informations.getSelectProperties())) throw new SQLException("error the same column name");

            return true;
        }

        return false;
    }

    public boolean selectAll(String query) {

        pattern = Pattern.compile("^select\\s\\*\\sfrom\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));

            return true;
        }

        return false;
    }

    public boolean selectWithCondition(String query) throws SQLException {

        pattern = Pattern.compile("^select\\s((?:(?:[a-zA-Z]\\w*,)*)(?:[a-zA-Z]\\w*))\\sfrom\\s([a-zA-Z]\\w*)\\swhere\\s([a-zA-Z]\\w*[<>=](?:(?:[0-9]+)|(?:'[^\\n']+')))$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(2));
            informations.setSelectProperties(matcher.group(1));
            informations.setCondition(matcher.group(3));

            if (!CheckLength(informations.getSelectProperties())) throw new SQLException("error the same column name");

            return true;
        }

        return false;
    }

    public boolean selectAllWithCondition(String query) {

        pattern = pattern.compile("^select\\s\\*\\sfrom\\s([a-zA-Z]\\w*)\\swhere\\s([a-zA-Z]\\w*[<>=](?:(?:[0-9]+)|(?:'[^\\n']+')))$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));
            informations.setCondition(matcher.group(2));

            return true;

        }

        return false;

    }

    public boolean delete(String query) {

        pattern = Pattern.compile("^delete\\sfrom\\s([a-zA-Z]\\w*)\\swhere\\s([a-zA-Z]\\w*[<>=](?:[0-9]+|'[^,\\n']+'))$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));//when you work on delete you need only table name and the condition to delete
            informations.setCondition(matcher.group(2));

            return true;
        }

        return false;
    }

    public boolean deleteAll(String query) {

        pattern = Pattern.compile("^delete\\sfrom\\s([a-zA-Z]\\w*)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));

            return true;
        }

        return false;
    }

    //after the insert dont forget that not condition the user enter equal number from the columns and value you should check it
    public boolean insert(String query) throws SQLException {

        pattern = Pattern.compile("^insert\\sinto\\s([a-zA-Z]\\w*)\\(((?:(?:[a-zA-Z]\\w*,)*)(?:[a-zA-Z]\\w*))\\)values\\(((?:(?:(?:(?:[0-9]+)|(?:'[^,\\n']+')),)*)(?:(?:[0-9]+)|(?:'[^,\\n']+')))\\)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));
            informations.setInsertIntoProperties(matcher.group(2),matcher.group(3)); // group2 for the name of the columns and group3 for values we need the two to make properties of the insert

            if (!CheckLength(informations.getInsertIntoPropertiesName())) throw new SQLException("error the same column name");

            return true;
        }

        return false;
    }

    public boolean insertWithoutColumnsName(String query) {

        pattern = Pattern.compile("^insert\\sinto\\s([a-zA-Z]\\w*)\\svalues\\(((?:(?:(?:(?:[0-9]+)|(?:'[^,\\n']+')),)*)(?:(?:[0-9]+)|(?:'[^,\\n']+')))\\)$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {
            informations.setTableName(matcher.group(1));
            informations.setInsertIntoProperties(null,matcher.group(2)); // group2 for the name of the columns and group3 for values we need the two to make properties of the insert


            return true;
        }

        return false;
    }

    public boolean update(String query) throws SQLException {

        pattern = Pattern.compile("^update\\s([a-zA-Z]\\w*)\\sset\\s((?:(?:[a-zA-Z]\\w*=(?:(?:[0-9]+)|(?:'[^\\n']+')),)*)(?:[a-zA-Z]\\w*=(?:(?:[0-9]+)|(?:'[^\\n']+'))))\\swhere\\s([a-zA-Z]\\w*[<>=](?:(?:[0-9]+)|(?:'[^\\n']+')))$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));
            informations.setUpdateProperties(matcher.group(2));
            informations.setCondition(matcher.group(3));

            if (!CheckLength(informations.getUpdatePropertiesName())) throw new SQLException("error the same column name");

            return true;
        }

        return false;
    }

    public boolean updateWithoutCondition(String query) throws SQLException {

        pattern = Pattern.compile("^update\\s([a-zA-Z]\\w*)\\sset\\s((?:(?:[a-zA-Z]\\w*=(?:(?:[0-9]+)|(?:'[^\\n']+')),)*)(?:[a-zA-Z]\\w*=(?:(?:[0-9]+)|(?:'[^\\n']+'))))$");
        matcher = pattern.matcher(query);

        if (matcher.find()) {

            informations.setTableName(matcher.group(1));
            informations.setUpdateProperties(matcher.group(2));

            if (!CheckLength(informations.getUpdatePropertiesName())) throw new SQLException("error the same column name");

            return true;
        }

        return false;

    }

    private boolean CheckLength(String[] array){
        Set<String> s = new HashSet<>();
        s.addAll(Arrays.asList(array));
        return s.size() == array.length;
    }

}
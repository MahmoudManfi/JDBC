import eg.edu.alexu.csd.oop.db.cs58.SyntaxChecker;
        import org.junit.Assert;
        import org.junit.Test;

import java.sql.SQLException;

public class SyntaxCheckerTests {
    SyntaxChecker syntaxChecker = new SyntaxChecker();

    @Test
    public void testCreateDatabase() {
        String input = "CREATE DATABASE database1".toLowerCase();
        Assert.assertEquals("Failed to make database ", true, syntaxChecker.createDatabase(input));


        input = "CREATE DATABASE 2database1".toLowerCase();
        Assert.assertEquals("accepted wrong database query", false, syntaxChecker.createDatabase(input));


        input = "CREATEDATABASE database1".toLowerCase();
        Assert.assertEquals("accepted wrong database query ", false, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE database1 CREATE DATABASE database1".toLowerCase();
        Assert.assertEquals("accepted wrong database query ", false, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE database1".toLowerCase();
        Assert.assertEquals("Failed to make database ", true, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE 2932323".toLowerCase();
        Assert.assertEquals("accepted wrong database query ", false, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE 2C".toLowerCase();
        Assert.assertEquals("accepted wrong database query", false, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE ".toLowerCase();
        Assert.assertEquals("accepted wrong database query ", false, syntaxChecker.createDatabase(input));

        input = "CREATE DATABASE S_2".toLowerCase();
        Assert.assertEquals("Failed to make database ", true, syntaxChecker.createDatabase(input));

    }

    @Test
    public void testDropTable(){
        String input = "drop DATABASE ydatabase1".toLowerCase();
        Assert.assertEquals("Failed drop  database ", true, syntaxChecker.dropDatabase(input));

        input = "drop database data_base".toLowerCase();
        Assert.assertEquals("Failed to drop  database ", true, syntaxChecker.dropDatabase(input));

    }

    @Test
    public void testInsertIntoTable() throws SQLException {
        String input = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway')".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("Failed to insert in a table ", true, syntaxChecker.insert(input));

        input = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', Norway')".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("Accepted query with incomplete  singlequotes ", false, syntaxChecker.insert(input));

        input = " INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("Failed to insert in a table", true, syntaxChecker.insert(input));


        input = " INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1, value3, '4')".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("accepted wrong insert query ", false, syntaxChecker.insert(input));


        input = " INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1, 'va'lue3', '4')".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("accepted wrong insert query ", false, syntaxChecker.insert(input));

        input = " INSERT INTO table_name4(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1, 'va,lue3', '4')".trim().toLowerCase().replaceAll("\\s+", " ");;
        Assert.assertEquals("accepted wrong insert query ", false, syntaxChecker.insert(input));


    }





}

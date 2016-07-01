package fasade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lapost48 on 6/23/2016.
 */
public class DatabaseInformation {

    private Connection connection;

    private String databaseName;
    private String tableName;
    private String[] columnNames;
    private String user;
    private String password;
    private int numberOfColumns;

    public DatabaseInformation() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            System.exit(1);
        }
    }



    private void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:" + databaseName, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    public void printAll() {
        System.out.println(databaseName);
        System.out.println(tableName);
        for(String s:columnNames)
            System.out.print(s + " ");
        System.out.println("\n" + user);
        System.out.println(password);
    }
    */

}

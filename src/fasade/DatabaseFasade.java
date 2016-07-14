package fasade;

import java.sql.*;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by lapost48 on 6/23/2016.
 */
public abstract class DatabaseFasade {

    private final boolean testing = true;

    private Connection connection;

    protected String databaseName;
    protected String tableName;
    protected HashMap<String, String> columnNames;
    protected String user;
    protected String password;
    protected int numberOfColumns;

    public DatabaseFasade() {
        if(!testing) {
            try {
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
            } catch (Exception e) {
                System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
                e.printStackTrace();
                System.exit(91);
            }
        }
    }

    protected void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:" + databaseName, user, password);
        } catch(SQLException e) {
            System.err.println("Error connecting to database!");
            System.err.println(e.getMessage());
        }
    }

    protected ResultSet runQuery(String query) throws SQLException {
        ResultSet ret = null;
        openConnection();
        Statement statement = connection.createStatement();
        try {
            ret = statement.executeQuery(query);
        } finally {
            statement.close();
            closeConnection();
        }

        return ret;
    }

    protected boolean run(String query) throws SQLException {
        boolean ret;
        openConnection();
        Statement statement = connection.createStatement();
        try {
            ret = statement.execute(query);
        } finally {
            statement.close();
            closeConnection();
        }

        return ret;
    }

    protected void closeConnection() {
        try {
            connection.close();
        } catch(SQLException e) {
            System.err.println("Error disconnecting from database!");
            System.err.println(e.getMessage());
        }
    }

    public String getColumnNames() {
        String ret = "";
        for (String s : columnNames.values()) {
            ret += s + ",";
        }
        return ret.substring(0, ret.length());
    }

}

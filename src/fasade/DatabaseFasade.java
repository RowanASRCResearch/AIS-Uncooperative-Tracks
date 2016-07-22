package fasade;

import java.sql.*;
import java.util.HashMap;

/**
 * @author: Nick LaPosta
 */
public abstract class DatabaseFasade {

    private final boolean testing = true;

    private Connection connection;
    private String driver;

    protected String databaseName;
    protected String tableName;
    protected HashMap<String, String> columnNames;
    protected String user;
    protected String password;
    protected int numberOfColumns;

    public DatabaseFasade() {
        if(!testing) {
            try {
                Class.forName(driver);
            } catch (Exception e) {
                System.err.println("ERROR: failed to load MySQL JDBC driver.");
                e.printStackTrace();
                System.exit(91);
            }
        }
    }

    protected void openConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/:" + databaseName, user, password);
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

    public String[] getColumnNames() {
        String[] ret = new String[columnNames.size()];
        int index = 0;
        for(String s : columnNames.values()) {
            if(!s.equals("ID")) {
                System.out.println(s);
                ret[index] = s;
                index++;
            }
        }
        return ret;
    }

}

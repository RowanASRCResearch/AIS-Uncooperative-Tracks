package fasade;

import java.sql.*;
import java.util.LinkedHashMap;

/**
 * @author: Nick LaPosta
 */
public abstract class DatabaseFasade {

    private final boolean testing = true;
    protected Connection connection;
    protected String databaseName;
    protected String[] tableNames;
    protected LinkedHashMap<String, String> columnNames;
    protected String user;
    protected String password;
    protected int numberOfColumns;
    private String driver;

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
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName + "?useSSL=false", "root", "");
        } catch(SQLException e) {
            System.err.println("Error connecting to database!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * USER MUST MANUALLY CLOSE THE CONNECTION WHEN USING THIS FUNCTION
     *
     * @param query
     * @return
     * @throws SQLException
     */
    protected ResultSet getQuery(String query) throws SQLException {
        ResultSet ret = null;
        openConnection();
        Statement statement = connection.createStatement();
        ret = statement.executeQuery(query);

        return ret;
    }

    protected boolean insertQuery(String query) throws SQLException {
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
        String[] ret = new String[columnNames.size()-1];
        int index = 0;
        for(String s : columnNames.values()) {
            if(!s.equals("ID")) {
                ret[index] = s;
                index++;
            }
        }
        return ret;
    }

}

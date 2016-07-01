package fasade;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by lapost48 on 6/23/2016.
 */
public abstract class DatabaseFasade {

    private final boolean testing = true;

    protected Connection connection;

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

    protected void openConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:" + databaseName, user, password);
    }

    protected ResultSet executeQuery(String query) {
        ResultSet ret = null;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ret = statement.executeQuery(query);
            closeConnection();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    protected void closeConnection() throws SQLException {
        connection.close();
    }

}

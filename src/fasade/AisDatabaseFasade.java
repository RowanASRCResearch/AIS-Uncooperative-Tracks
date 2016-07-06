package fasade;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lapost48 on 7/1/2016.
 */
public class AisDatabaseFasade extends DatabaseFasade {

    public AisDatabaseFasade() {
        super();
    }

    // Example Function
    public ResultSet getSpeeds(String id) {
        String col = columnNames.get("speed");
        String query = "SELECT " + columnNames.get("time") + "," + col + " FROM " + tableName + " where " + columnNames.get("id") + "=" + id;

        try {
            return runQuery(query);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    // End Example Function

    public boolean createTable() {
        String query = "CREATE TABLE PUBLIC.AISDATA\n" +
                "(ID INTEGER,\n" +
                columnNames.get("time") + " VARCHAR(25),\n" +
                columnNames.get("id") + " VARCHAR(25),\n" +
                columnNames.get("latitude") + " FLOAT,\n" +
                columnNames.get("longitude") + " FLOAT,\n" +
                columnNames.get("course") + " FLOAT,\n" +
                columnNames.get("speed") + " FLOAT,\n" +
                columnNames.get("heading") + " INTEGER,\n" +
                columnNames.get("imo") + " VARCHAR(25),\n" +
                columnNames.get("name") + " VARCHAR(50),\n" +
                columnNames.get("callsign") + " VARCHAR(25),\n" +
                columnNames.get("type") + " VARCHAR(5),\n" +
                columnNames.get("bowLength") + " INTEGER,\n" +
                columnNames.get("sternLength") + " INTEGER,\n" +
                columnNames.get("c") + " INTEGER,\n" +
                columnNames.get("d") + " INTEGER,\n" +
                columnNames.get("draft") + " FLOAT,\n" +
                columnNames.get("destination") + " VARCHAR(25),\n" +
                columnNames.get("eta") + " VARCHAR(25))";
        String kmlQuery = "CREATE TABLE PUBLIC.KMLPOINTS ("+ columnNames.get("time")+" INT , "+
                columnNames.get("latitude")+" FLOAT, "+ columnNames.get("longitude")+" FLOAT);";
        try {
            runQuery(query);
            runQuery(kmlQuery);
        } catch(SQLException e) {
            return false;
        }
        return true;
    }

}

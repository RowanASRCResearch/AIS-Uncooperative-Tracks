package fasade;

import org.apache.commons.csv.CSVRecord;
import prediction.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public boolean insertCsvEntry(CSVRecord record) {
        //goes through each portion of the record and appends it to the string
        String query = "INSERT INTO " + tableName
                + " VALUES (00,"
                + record.get(columnNames.get("time"))
                + record.get(columnNames.get("id"))
                + Float.parseFloat(record.get(columnNames.get("latitude")))
                + Float.parseFloat(record.get(columnNames.get("longitude")))
                + Float.parseFloat(record.get(columnNames.get("course")))
                + Float.parseFloat(record.get(columnNames.get("speed")))
                + Integer.parseInt(record.get(columnNames.get("heading")))
                + record.get(columnNames.get("imo"))
                + record.get(columnNames.get("name"))
                + record.get(columnNames.get("name"))
                + record.get(columnNames.get("type"))
                + Integer.parseInt(record.get(columnNames.get("bowLength")))
                + Integer.parseInt(record.get(columnNames.get("sternLength")))
                + Integer.parseInt(record.get(columnNames.get("c")))
                + Integer.parseInt(record.get(columnNames.get("d")))
                + Float.parseFloat(record.get(columnNames.get("draft")))
                + record.get(columnNames.get("destination"))
                + record.get(columnNames.get("eta"));
        try {
            runQuery(query);
        } catch(SQLException e) {
            return false;
        }
        return true;
    }

    public boolean createTable() {
        String query = "CREATE TABLE PUBLIC.AISDATA "
                + "(ID INTEGER,"
                + columnNames.get("time") + " VARCHAR(25),"
                + columnNames.get("id") + " VARCHAR(25),"
                + columnNames.get("latitude") + " FLOAT,"
                + columnNames.get("longitude") + " FLOAT,"
                + columnNames.get("course") + " FLOAT,"
                + columnNames.get("speed") + " FLOAT,"
                + columnNames.get("heading") + " INTEGER,"
                + columnNames.get("imo") + " VARCHAR(25),"
                + columnNames.get("name") + " VARCHAR(50),"
                + columnNames.get("callsign") + " VARCHAR(25),"
                + columnNames.get("type") + " VARCHAR(5),"
                + columnNames.get("bowLength") + " INTEGER,"
                + columnNames.get("sternLength") + " INTEGER,"
                + columnNames.get("c") + " INTEGER,"
                + columnNames.get("d") + " INTEGER,"
                + columnNames.get("draft") + " FLOAT,"
                + columnNames.get("destination") + " VARCHAR(25),"
                + columnNames.get("eta") + " VARCHAR(25))";
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

    public ResultSet getData(String id, String date) {
        String query = "SELECT * FROM " + tableName
                + "WHERE (MMSI=" + id
                + " AND DATETIME LIKE %" + date + "%) "
                + "ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            return runQuery(query);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public int getVesselSize(String id) {
        String query = "SELECT "
                + columnNames.get("bowLength")
                + ", "
                + columnNames.get("sternLength")
                + " "
                + "FROM aisData WHERE MMSI=" + id;
        try {
            ResultSet rs = runQuery(query);
            return rs.getInt(columnNames.get("bowLength")) +rs.getInt(columnNames.get("sternLength"));
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public boolean insertLocation(int time, float latitude, float longitude) {
        String query = "INSERT INTO PUBLIC.KMLPOINTS VALUES (" + time + "," + latitude + "," + longitude + ")";
        try {
            runQuery(query);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Point> getKML() {
        String query = "SELECT * FROM PUBLIC.KMLPOINTS "
                + "ORDER BY "+ columnNames.get("time");
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = runQuery(query);
            while(rs.next())
                pointList.add(new Point(rs.getFloat("latitude"),rs.getFloat("longitude"), rs.getString("datetime")));
            return pointList;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Point> getKMLPath(String id) {
        String query = "SELECT * FROM PUBLIC.AISDATA "
                + "WHERE MMSI=" + id
                + " ORDER BY " + columnNames.get("time");
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = runQuery(query);
            while(rs.next())
                pointList.add(new Point(rs.getFloat("latitude"),rs.getFloat("longitude"), rs.getString("datetime")));
            return pointList;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Point> getPorts() {
        String query = "SELECT * FROM PUBLIC.PORTS";
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = runQuery(query);
            while(rs.next())
                pointList.add(new Point(rs.getFloat("latitude"),rs.getFloat("longitude"), rs.getString("datetime")));
            return pointList;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}

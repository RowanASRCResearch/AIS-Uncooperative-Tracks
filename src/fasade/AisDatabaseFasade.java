package fasade;

import org.apache.commons.csv.CSVRecord;
import prediction.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author: Nick LaPosta
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
                + " VALUES ("
                + record.get(columnNames.get("time"))
                + record.get(columnNames.get("id"))
                + Float.parseFloat(record.get(columnNames.get("latitude")))
                + Float.parseFloat(record.get(columnNames.get("longitude")))
                + Float.parseFloat(record.get(columnNames.get("course")))
                + Float.parseFloat(record.get(columnNames.get("speed")))
                + Integer.parseInt(record.get(columnNames.get("heading")))
                + record.get(columnNames.get("imo"))
                + record.get(columnNames.get("name"))
                + record.get(columnNames.get("callsign"))
                + record.get(columnNames.get("type"))
                + Integer.parseInt(record.get(columnNames.get("bowLength")))
                + Integer.parseInt(record.get(columnNames.get("sternLength")))
                + Integer.parseInt(record.get(columnNames.get("c")))
                + Integer.parseInt(record.get(columnNames.get("d")))
                + Float.parseFloat(record.get(columnNames.get("draught")))
                + record.get(columnNames.get("destination"))
                + record.get(columnNames.get("eta"));
        try {
            return run(query);
        } catch(SQLException e) {
            return false;
        }
    }

    public boolean createTable() {
        String query = "CREATE TABLE " + tableName + " "
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
            run(query);
            run(kmlQuery);
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

    public String[] getLastContact(String id, String date) {
        String query = "SELECT * FROM " + tableName
                + " WHERE MMSI=" + id
                + " AND DATETIME LIKE '%" + date + "%'"
                + " ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = runQuery(query);
            rs.last();
            String[] dateSplit = rs.getString(columnNames.get("time")).split(" ");
            return dateSplit;
        } catch(SQLException e) {
            System.err.println(e.getMessage() + "\n" + query);
            return null;
        }
    }

    public float[] getLastLocation(String id, String date) {
        String query = "SELECT * FROM " + tableName
                + "WHERE (MMSI=" + id
                + " AND DATETIME LIKE %" + date + "%) "
                + "ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = runQuery(query);
            rs.last();
            float[] latLong = {rs.getFloat(columnNames.get("latitude")), rs.getFloat(columnNames.get("longitude"))};
            return latLong;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public float getLastSpeed(String id, String date) {
        String query = "SELECT * FROM " + tableName
                + "WHERE (MMSI=" + id
                + " AND DATETIME LIKE %" + date + "%) "
                + "ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = runQuery(query);
            rs.last();
            float speed = rs.getFloat(columnNames.get("speed"));
            return speed;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return Float.MAX_VALUE;
        }
    }

    public float getLastCourse(String id, String date) {
        String query = "SELECT * FROM " + tableName
                + "WHERE (MMSI=" + id
                + " AND DATETIME LIKE %" + date + "%) "
                + "ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = runQuery(query);
            rs.last();
            float course = rs.getFloat(columnNames.get("course"));
            return course;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return Float.MAX_VALUE;
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
            return run(query);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
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

package fasade;

import gathering.RadiusGenerator;
import gathering.Station;
import org.apache.commons.csv.CSVRecord;
import prediction.Point;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author: Nick LaPosta
 */
public class AisDatabaseFasade extends DatabaseFasade {

    public AisDatabaseFasade() {
        super();
    }

    public boolean insertCsvEntry(CSVRecord record) {
        //goes through each portion of the record and appends it to the string

        String query = "INSERT INTO " + tableNames[0] + "(";
        for(String col: getColumnNames())
            query += col + ",";
        query = query.substring(0, query.length()-1) + ") VALUES ('"
                + record.get(columnNames.get("time")) + "', "
                + record.get(columnNames.get("id")) + ", "
                + Float.parseFloat(record.get(columnNames.get("latitude"))) + ", "
                + Float.parseFloat(record.get(columnNames.get("longitude"))) + ", "
                + Float.parseFloat(record.get(columnNames.get("course"))) + ", "
                + Float.parseFloat(record.get(columnNames.get("speed"))) + ", "
                + Integer.parseInt(record.get(columnNames.get("heading"))) + ", '"
                + record.get(columnNames.get("imo")) + "', '"
                + record.get(columnNames.get("name")) + "', '"
                + record.get(columnNames.get("callsign")) + "', "
                + record.get(columnNames.get("type")) + ", "
                + Integer.parseInt(record.get(columnNames.get("bowLength"))) + ", "
                + Integer.parseInt(record.get(columnNames.get("sternLength"))) + ", "
                + Integer.parseInt(record.get(columnNames.get("c"))) + ", "
                + Integer.parseInt(record.get(columnNames.get("d"))) + ", "
                + Float.parseFloat(record.get(columnNames.get("draught"))) + ", '"
                + record.get(columnNames.get("destination")) + "', '"
                + record.get(columnNames.get("eta"))
                + "');";
        try {
            return insertQuery(query);
        } catch(SQLException e) {
            return false;
        }
    }

    // TODO: Table should be created by MySQL schema file, not explicit command.
/*    public boolean createTable() {
        execute .sql file
    }*/

    public String[] getLastContact(String id) {
        String query = "SELECT * FROM " + tableNames[0]
                + " WHERE " + columnNames.get("id") + "=" + id
                + " ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.first();
            String[] dateSplit = rs.getString(columnNames.get("time")).split(" ");
            return dateSplit;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public float[] getLastLocation(String id) {
        String query = "SELECT * FROM " + tableNames[0]
                + " WHERE MMSI=" + id
                + " ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = getQuery(query);
            rs.first();
            float[] latLong = {rs.getFloat(columnNames.get("latitude")), rs.getFloat(columnNames.get("longitude"))};
            return latLong;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }finally {
            closeConnection();
        }
    }

    public float getLastSpeed(String id) {
        String query = "SELECT * FROM " + tableNames[0]
                + " WHERE MMSI=" + id
                + " ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = getQuery(query);
            rs.first();
            float speed = rs.getFloat(columnNames.get("speed"));
            return speed;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return Float.MAX_VALUE;
        } finally {
            closeConnection();
        }
    }

    public float getLastCourse(String id) {
        String query = "SELECT * FROM " + tableNames[0]
                + " WHERE MMSI=" + id
                + " ORDER BY " + columnNames.get("time")
                + " DESC LIMIT 1";
        try {
            ResultSet rs = getQuery(query);
            rs.first();
            float course = rs.getFloat(columnNames.get("course"));
            return course;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return Float.MAX_VALUE;
        } finally {
            closeConnection();
        }
    }

    public int getVesselSize(String id) {
        String query = "SELECT "
                + columnNames.get("bowLength")
                + ", "
                + columnNames.get("sternLength")
                + " "
                + "FROM " + tableNames[0] + " WHERE MMSI=" + id;
        try {
            ResultSet rs = getQuery(query);
            rs.first();
            return rs.getInt(columnNames.get("bowLength")) + rs.getInt(columnNames.get("sternLength"));
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        } finally {
            closeConnection();
        }
    }

    public boolean insertLocation(float latitude, float longitude) {
        String query = "INSERT INTO " + tableNames[1] + "(LATITUDE, LONGITUDE) VALUES (" + latitude + "," + longitude + ")";
        try {
            return insertQuery(query);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public Boolean insertStations(int id, float latitude, float longitude) {


        String query = "INSERT INTO " + tableNames[2] + "(ID, LATITUDE, LONGITUDE) VALUES (" + latitude + "," + longitude + ")";
        try {
            return insertQuery(query);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Point> getKML() {
        String query = "SELECT * FROM " + tableNames[1]
                + " ORDER BY ID";
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = getQuery(query);
            while (rs.next())
                pointList.add(new Point(rs.getFloat("latitude"), rs.getFloat("longitude"), "ID"));
            return pointList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Station> getStations() {
        String query = "SELECT * FROM " + tableNames[2]
                + " ORDER BY ID";
        try {
            ArrayList<Station> stations = new ArrayList<>();
            ResultSet rs = getQuery(query);
            while(rs.next())
                stations.add(new Station(rs.getInt("id"), rs.getFloat("latitude"), rs.getFloat("longitude")));
            return stations;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Point> getKMLPath(String id) {
        String query = "SELECT * FROM " + tableNames[0]
                + " WHERE MMSI=" + id
                + " ORDER BY ID";
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = getQuery(query);
            while(rs.next())
                pointList.add(new Point(rs.getFloat("latitude"),rs.getFloat("longitude"), "ID"));
            return pointList;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    public ArrayList<Point> getPorts() {
        String query = "SELECT * FROM PUBLIC.PORTS";
        try {
            ArrayList<Point> pointList = new ArrayList<>();
            ResultSet rs = getQuery(query);
            while(rs.next())
                pointList.add(new Point(rs.getFloat("latitude"),rs.getFloat("longitude"), rs.getString("datetime")));
            return pointList;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

}

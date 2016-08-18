package gathering;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import prediction.Point;

/**
 * Created by Eliakah Kakou
 * This class takes coordinates and a distance as input and
 * returns a list of points making up a circle of the specified radius
 */
public class RadiusGenerator {

    public Point[] points; //list of points
    float rad = 0; //circle radius
    Point original; //center point
    int pointNum = 0;
    private float[] angles; //list of angles of each points in respective order

    /**
     * Constructor
     *
     * @param lat
     * @param lng
     * @param pointNum
     * @param rad
     */
    public RadiusGenerator(float lat, float lng, int pointNum, float rad) {
        original = new Point(lat, lng);
        this.rad = rad;
        this.pointNum = pointNum;
        angles = new float[pointNum];
        points = new Point[pointNum];
        getCirclePoints();
        for (int i = 0; i < angles.length; i++) {
            points[i] = genPoint(angles[i], rad);
        }
        System.out.println(Arrays.toString(angles));

    }

    public static void main(String[] args) throws IOException {
        // write your code here
        RadiusGenerator g = new RadiusGenerator(0, 0, 30, 5.66f);
        System.out.println(Arrays.toString(g.points));
        System.out.println(g.contains(g.points, new Point(0, 0)));
        //Point p = g.genPoint(360, 5.66f);
        // System.out.println(p.getLatitude());
        // System.out.println(p.getLongitude());

        g.scanStations("stations.csv");
    }

    /**
     * generates coordiantes of a point given angle and distance
     * @param angle
     * @param distance
     * @return p
     */
    Point genPoint(float angle, float distance) {
        Point p = new Point(0, 0);
        float height, width;



        //get height
        height = (float) (Math.sin(Math.toRadians(angle)) * distance);

        //get width
        width = (float) (Math.cos(Math.toRadians(angle)) * distance);


        p.setLatitude(original.getLatitude() + width);
        p.setLongitude(original.getLongitude() + height);

/*
        System.out.println(" <Placemark>\n" +
                "    <name>" + angle + "</name>\n" +
                "    <description></description>\n" +
                "    <Point>\n" +
                "      <coordinates>" + p.getLongitude() + "," + p.getLatitude() + "</coordinates>\n" +
                "    </Point>\n" +
                "  </Placemark> ");*/
        return p;
    }


    /**
     * generates all of the angles of the points in the circle
     */
    private void getCirclePoints() {
        float increment = 360 / pointNum;
        float angle = 0;
        for (int i = 0; i < 30; i++) {

            angles[i] = angle;
            angle += increment;
        }

    }

    /**
     * checks if a point is contained in an area
     * @param polygon
     * @param p
     * @return
     */
    Boolean contains(Point[] polygon, Point p) {
        Polygon poly = new Polygon();
        for (int i = 0; i < polygon.length; i++) {
            poly.addPoint((int) polygon[i].getLatitude(), (int) polygon[i].getLongitude());
        }

        return poly.contains(p.getLatitude(), p.getLongitude());

    }

    /**
     * scanns file line by line and returns a list of stations object
     * @param fileName
     * @return stations
     * @throws IOException
     */
    private ArrayList<station> scanStations(String fileName) throws IOException {
        ArrayList<station> stations = new ArrayList<>();
        // Open the file
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;


        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            String[] holder = strLine.split(",");
            stations.add(new station(holder[0], holder[1], holder[2]));
            System.out.println(strLine);
        }

        //Close the input stream
        br.close();


        return stations;
    }


    /**
     * check database and return stations inside the radius
     * @return
     */
    ArrayList<String> getStations() {
        ArrayList<String> stations = new ArrayList<>();
        //in here we can check database for stations inside area
        return stations;
    }

    /**
     * station objects used to hold station information
     */
    private class station {
        String id;
        String lat;
        String lon;

        public station(String id, String lat, String lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }
}





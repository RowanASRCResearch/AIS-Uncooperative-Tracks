package gathering;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by Research on 8/10/2016.
 */
public class Gen {

    public Point[] points;
    float rad = 0;
    Point original;
    ArrayList<Point> radius = new ArrayList<>();
    private float[] angles;

    public Gen(float lat, float lng, int pointNum, float rad) {
        original = new Point(lat, lng);
        this.rad = rad;
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
        Gen g = new Gen(0, 0, 30, 5.66f);
        System.out.println(Arrays.toString(g.points));
        System.out.println(g.contains(g.points, new Point(0, 0)));
        //Point p = g.genPoint(360, 5.66f);
        // System.out.println(p.latitude);
        // System.out.println(p.longitude);

        g.scanStations("stations.txt");
    }

    Point genPoint(float angle, float distance) {
        Point p = new Point(0, 0);
        int quadrant = 1;
        float height, width;


        System.out.println(quadrant);
        //get height
        height = (float) (Math.sin(Math.toRadians(angle)) * distance);

        //get width
        width = (float) (Math.cos(Math.toRadians(angle)) * distance);


        p.latitude = (original.latitude + width);
        p.longitude = (original.longitude + height);


        System.out.println(" <Placemark>\n" +
                "    <name>" + angle + "</name>\n" +
                "    <description></description>\n" +
                "    <Point>\n" +
                "      <coordinates>" + p.longitude + "," + p.latitude + "</coordinates>\n" +
                "    </Point>\n" +
                "  </Placemark> ");
        return p;
    }


    private void getCirclePoints() {
        float increment = 360 / 30;
        float angle = 0;
        for (int i = 0; i < 30; i++) {

            angles[i] = angle;
            angle += increment;
        }

    }

    Boolean contains(Point[] polygon, Point p) {
        Polygon poly = new Polygon();
        for (int i = 0; i < polygon.length; i++) {
            poly.addPoint((int) polygon[i].latitude, (int) polygon[i].longitude);
        }

        return poly.contains(p.latitude, p.longitude);

    }

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


    ArrayList<String> getStations() {
        ArrayList<String> stations = new ArrayList<>();
        //in here we can check database for stations inside area
        return stations;
    }


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





package gathering.wind;

import java.awt.*;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
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

    }

    public static void main(String[] args) throws IOException {
        RadiusGenerator g = new RadiusGenerator(0, 0, 30, 5.66f);
        g.insertStations("stations.csv");
    }

    Point[] getCircle() {
        return points;
    }

    /**
     * generates coordiantes of a point given angle and distance
     * @param angle
     * @param distance
     * @return p
     */
    private Point genPoint(float angle, float distance) {
        Point p = new Point(0, 0);
        float height, width;



        //get height
        height = (float) (Math.sin(Math.toRadians(angle)) * distance);

        //get width
        width = (float) (Math.cos(Math.toRadians(angle)) * distance);


        p.setLatitude(original.getLatitude() + width);
        p.setLongitude(original.getLongitude() + height);
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
    private void insertStations(String fileName) throws IOException {
        AisDatabaseFasade fascade = new AisDatabaseFasade();
        JsonReader jr;
        try {
            jr = new JsonReader(new FileReader(new File("src/fasade/JSONTEST.json")));
            fascade = new Gson().fromJson(jr, AisDatabaseFasade.class);
            jr.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
        // Open the file
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;


        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            String[] holder = strLine.split(",");
            fascade.insertStations(Integer.parseInt(holder[0].trim()), Float.parseFloat(holder[2].trim()), Float.parseFloat(holder[1].trim()));
        }

        //Close the input stream
        br.close();


    }



}





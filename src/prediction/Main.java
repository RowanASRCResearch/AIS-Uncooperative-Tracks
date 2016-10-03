package prediction;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import io.KMLBuilder;

import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by lapost48 on 7/14/2016.
 */
public class Main {
    private static float vesselTurnRate;
    public static AisDatabaseFasade database;
    public static float travelTime;

    public static void main(String args[]) throws IOException {
        String filePath = "";
        int coverage = 1;

        //extract arguments
        if (args.length >= 3) {
            coverage = Integer.parseInt(args[2]);

        }

        if (args.length >= 4) {
            filePath = args[3];
        }

        //System.out.println(filePath);
        String mmsi = args[0];
        travelTime = Integer.parseInt(args[1]);

        //use fascade to pull latest vessel info by mmsi
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(new File("src/fasade/JSONTEST.json")));
            database = new Gson().fromJson(reader, AisDatabaseFasade.class);
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }

        //store info pulled
        Point initialCoordinates = new Point(database.getLastLocation(mmsi)[0], database.getLastLocation(mmsi)[1]);
        float vesselSpeed = database.getLastSpeed(mmsi);
        vesselSpeed = ((vesselSpeed * 1.8519999985024f) / 360); //knots to km per sec
        System.out.println("vessel speed Original " + vesselSpeed);
        System.out.println("vessel traveltime Original " + travelTime);
        float vesselCourse = database.getLastCourse(mmsi);
        vesselSize(mmsi);

        GeoVector vessel = new GeoVector(initialCoordinates, vesselSpeed, (int) vesselCourse);
        KMLBuilder builder;
        String tag;
        PathPredictor path = new PathPredictor();

        //AreaGenerator gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
        ArrayList<GeoVector> buoys = new ArrayList<>();
        if (filePath != "")
            buoys = generateBuoys(filePath);


        //from left to right bounds
        GeoVector vesselHolder = vessel;
        ArrayList<ArrayList<Point>> results = new ArrayList<>();
        float leftBound = getLeftRightBounds(true, vesselHolder);
        float rightBound = getLeftRightBounds(false, vesselHolder);
        //System.out.println("right " + rightBound);
        //System.out.println("left " + leftBound);

        if (leftBound > rightBound) {
            for (float i = leftBound; i <= 360; i++) {
                vesselHolder.vectorAngle = i;
                vessel.speed = vesselSpeed;
                results.add(path.getPath(vesselHolder.location, vesselHolder.getSpeed(), vesselHolder.getAngle(), travelTime, buoys));
                //System.out.println(i);
            }

            for (float i = 1; i <= rightBound; i++) {
                vesselHolder.vectorAngle = i;
                vessel.speed = vesselSpeed;
                results.add(path.getPath(vesselHolder.location, vesselHolder.getSpeed(), vesselHolder.getAngle(), travelTime, buoys));
                //System.out.println(i);
            }

        } else {

            for (float i = leftBound; i <= rightBound; i++) {
                vesselHolder.vectorAngle = i;
                vessel.speed = vesselSpeed;
                results.add(path.getPath(vesselHolder.location, vesselHolder.getSpeed(), vesselHolder.getAngle(), travelTime, buoys));
                //System.out.println(i);
            }
        }

        ArrayList<Point> polygon = new ArrayList<Point>();
        for (int i = 0; i < results.get(0).size(); i++) {
            polygon.add(results.get(0).get(i));
        }
        for (int i = 1; i < results.size(); i++) {
            polygon.add(results.get(i).get(results.get(i).size() - 1));
        }
        for (int i = results.get(results.size() - 1).size() - 1; i >= 0; i--) {
            polygon.add(results.get(results.size() - 1).get(i));
        }


        //create the kml
        builder = new KMLBuilder();
        tag = "";
        if(coverage == 1){ // Produce Paths
             tag = builder.placemark(initialCoordinates,"Point of Origin", "Loss of Signal" );
             for (int i = 0; i < results.size(); i++) {
                tag += "\n" + builder.path(results.get(i), "path");
                }
        }else{ // Produce Polygon
            tag = builder.placemark(initialCoordinates,"Point of Origin", "Loss of Signal" );
            for (int i = 0; i < polygon.size(); i++) {
                tag += "\n" + builder.polygon(polygon, new ArrayList<Point>(), "Area of prediction");
            }
        }

        builder.createFile(tag);

        for (int i = 0; i <buoys.size() ; i++) {
            System.out.println(buoys.get(i).location.latitude+","+buoys.get(i).location.longitude+","+buoys.get(i).getMagnitude()+","+buoys.get(i).getAngle());
        }

       /* ArrayList<GeoVector> tempList = generateBuoys(filePath);
        for (int i = 0; i < tempList.size(); i++) {
            GeoVector temp = tempList.get(i);
            System.out.println(temp.getSpeed() + "," + temp.getAngle());
        }
        System.out.println("LAST ELEMENT OF TEMPLIST: " + tempList.get(tempList.size()-1));

*/
    }


    /**
     * This method will determine whether or not the vessel's length is greater than or equal to 100 meters.
     * If it is, then is sets the vesselTurnRate to 3 degrees. Otherwise, vesselTurnRate is set to 5 degrees.
     *
     * @param mmsi the targeted vessel's MMSI number
     */
    private static void vesselSize(String mmsi) {
        //if the total length of the vessel is greater than or equal to 100 meters
        if (database.getVesselSize(mmsi) >= 100) {
            vesselTurnRate = 3f;
            return;
        }
        vesselTurnRate = 5f;
    }

    /**
    * This method will return either the left or right bound of the vessel based upon turning speed.
    * @param isLeft -> if true, returns left bound, else returns right bound.
     */
    public static float getLeftRightBounds(boolean isLeft, GeoVector vessel)
    {
        float amountTurned = vesselTurnRate * travelTime;
        float bound;
        if(amountTurned >= 90)
            amountTurned = 90;


            if (isLeft) {
                bound = vessel.getAngle() - amountTurned;
            } else {
                bound = vessel.getAngle() + amountTurned;
            }

            if (bound > 360f)
                bound = bound - 360f;
            else if (bound < 0f)
                bound = bound + 360f;


        System.out.println("vessel angle "+vessel.getAngle());
        System.out.println("amount turned: " + amountTurned);
        return bound;
    }

    /**
     * This method will generate the random angles and speeds for the buoys based around the start and end point provided
     * @param start
     * @param end
     * @return List of GeoVector buoys
     */
    static ArrayList<GeoVector> generateGrid(Point start, Point end) {
        Random rn = new Random();
        float xIncrement = (end.latitude - start.latitude) / 12;
        float yIncrement = (end.longitude - start.longitude) / 12;
        ArrayList<GeoVector> buoys = new ArrayList<>();

        for (float i = start.longitude; i <= end.longitude; i += yIncrement) {


            for (float j = start.latitude; j <= end.latitude; j += xIncrement) {
                int angle = rn.nextInt(360 - 0 + 1) + 0;
                int sp = rn.nextInt(66 - 56 + 1) + 56;
                buoys.add(new GeoVector(new Point(j, i), ((sp* 1.8519999985024f)/360)/100, (int)angle)); //knots to km per sec, angle));
                //System.out.println(i + ", " + j);
            }
        }

        return buoys;
    }

    /**
     * Reads from a file of a list of Buoys to generate a previous run of the algorithm in KML format
     * @param fileName
     * @return List of GeoVectors to form points from a previous run
     */
    static ArrayList<GeoVector> generateBuoys(String fileName)
    {
        ArrayList<GeoVector> bouys = new ArrayList<GeoVector>();
        String thisLine;
        String result = "";
        String[] points;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((thisLine = br.readLine()) != null) {
                //System.out.println(thisLine);
                result = thisLine;
                points = result.split(",");
                GeoVector temp = new GeoVector(new Point(Float.parseFloat(points[0]), Float.parseFloat(points[1])),
                        Float.parseFloat(points[2]), (int)Float.parseFloat(points[3]));
                bouys.add(temp);
            }
            br.close();

        } catch(Exception e) {
            e.printStackTrace();

        }
        return bouys;
    }
}

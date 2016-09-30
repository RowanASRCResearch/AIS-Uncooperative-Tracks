package prediction;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import io.KMLBuilder;

import java.awt.geom.Area;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by lapost48 on 7/14/2016.
 */
public class Main {
    private static float vesselTurnRate;
    public static AisDatabaseFasade database;
    public static float travelTime;

    public static void main(String args[]) throws IOException {
        //extract arguments
        String mmsi = args[0];
        //String date = args[1];
        travelTime = Integer.parseInt(args[1]);
        //int isOld;
        /*
        if(args.length < 4){
            isOld = 1; }
        else {
            //isOld = Integer.parseInt(args[3]); }// 0 is old, 1 is new.
*/

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
        vesselSpeed = vesselSpeed * 0.000514444f;
        System.out.print("vessel speed" + vesselSpeed);
        float vesselCourse = database.getLastCourse(mmsi);
        vesselSize(mmsi);

        GeoVector vessel = new GeoVector(initialCoordinates, vesselSpeed, (int) vesselCourse);
        KMLBuilder builder;
        String tag;
        PathPredictor path = new PathPredictor();

        //System.out.println("Location: " + vessel.location + " speed: " + vessel.speed + " angle: " + vessel.getAngle() + " travelTime: " + travelTime);
        //results = path.getPath(vessel.location, vessel.getSpeed(), vessel.getAngle(), travelTime);

        //from left to right bounds
        GeoVector vesselHolder = vessel;
        ArrayList<ArrayList<Point>> results = new ArrayList<>(); //= gen.execute(buoys);
        float leftBound = getLeftRightBounds(true, vesselHolder);
        float rightBound = getLeftRightBounds(false, vesselHolder);
        Point currentLoc;
        for (float i = leftBound; i <= rightBound; i++) {
            vesselHolder.vectorAngle = i;
            results.add(path.getPath(vesselHolder.location, vesselHolder.getSpeed(), vesselHolder.getAngle(), travelTime));
        }

        //create the kml
        builder = new KMLBuilder();
        tag = "";
        for (int i = 0; i < results.size(); i++) {
            tag += "\n" + builder.path(results.get(i), "path");
        }

            //creating vessel vector
            //AreaGenerator gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);

           // ArrayList<GeoVector> buoys = gen.generateGrid(new Point(35, 15), new Point(37, 17));


        builder.createFile(tag);

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

    /*
    * This method will return either the left or right bound of the vessel based upon turning speed.
    * @param isLeft -> if true, returns left bound, else returns right bound.
     */
    public static float getLeftRightBounds(boolean isLeft, GeoVector vessel)
    {
        float amountTurned = vesselTurnRate * travelTime;
        float bound;
        if(isLeft)
        {
            bound = vessel.getAngle()-amountTurned;
        }
        else {
            bound = vessel.getAngle() + amountTurned;
        }

        if(bound > 360f)
            bound = bound - 360f;
        else if(bound < 0f)
            bound = bound + 360f;

        return bound;
    }
}

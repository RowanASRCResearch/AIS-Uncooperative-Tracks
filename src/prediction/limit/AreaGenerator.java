package prediction.limit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import io.KMLBuilder;
import prediction.Controller;
import prediction.PathPredictor;
import prediction.Point;
import sun.text.resources.cldr.ia.FormatData_ia;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by eliakah on 9/20/16.
 */
//TODO: I just need to create the vectors and apply them to the paths, also generate the fake bouys in an area to demonstrate the possibility

public class AreaGenerator {

    public static AisDatabaseFasade database;
    private static Vector vessel;
    private static int travelTime;
    private static float vesselTurnRate;

    public AreaGenerator(Vector vessel, int travelTime) {
        this.vessel = vessel;
        this.travelTime = travelTime;
    }

    public static void main(String args[]) {
        //extract arguments
        String mmsi = args[0];
        String date = args[1];
        int travelTime = Integer.parseInt(args[2]);


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
        float vesselCourse = database.getLastCourse(mmsi);
        vesselSize(mmsi);

        //creating vessel vector
        Vector vessel = new Vector(initialCoordinates, vesselSpeed, vesselCourse);
        AreaGenerator gen = new AreaGenerator(vessel, travelTime);

        ArrayList<Point> result = execute();

        //create the kml

        //KMLBuilder builder = new KMLBuilder("testKML", "test for the path in a certain direction ");

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).latitude + ", " + result.get(i).longitude);
        }

    }

    static ArrayList<Point> execute() {
        ArrayList<Point> path = new ArrayList<>();

        PathPredictor predictor = new PathPredictor(vessel.location, vessel.getDirection(), vessel.getSpeed());
        path.add(new Point(vessel.location.latitude, vessel.location.longitude));
        for (int i = 0; i < travelTime; i++) {

            path.add(predictor.execute());
            vessel.location = predictor.execute();
            predictor = new PathPredictor(vessel.location, vessel.getDirection(), vessel.getSpeed());
        }

        return path;
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

    Vector ApplyWeatherData() {
        Vector newVessel = null; //temporary
        //determine

        return newVessel;
    }


}

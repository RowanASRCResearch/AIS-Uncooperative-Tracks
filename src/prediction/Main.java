package prediction;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import io.KMLBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lapost48 on 7/14/2016.
 */
public class Main {
    private static float vesselTurnRate;
    public static AisDatabaseFasade database;
    public static void main(String args[]) throws IOException {
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
        System.out.print("vessel speed" + vesselSpeed);
        float vesselCourse = database.getLastCourse(mmsi);
        vesselSize(mmsi);


        //creating vessel vector
        GeoVector vessel = new GeoVector(initialCoordinates, vesselSpeed, (int) vesselCourse);
        AreaGenerator gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
        ArrayList<GeoVector> buoys = gen.generateGrid(new Point(35, 15), new Point(37, 17));

        ArrayList<Point> result = gen.execute(buoys);


        //create the kml

        //KMLBuilder builder = new KMLBuilder("testKML", "test for the path in a certain direction ");

        KMLBuilder builder = new KMLBuilder();
        String tag = builder.path(result, "path");
        builder.createFile(tag);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).latitude + ", " + result.get(i).longitude);
        }

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
}

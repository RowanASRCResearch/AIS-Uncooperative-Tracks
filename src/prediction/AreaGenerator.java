package prediction;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import gathering.wind.RadiusGenerator;
import io.KMLBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by eliakah on 9/20/16.
 */
//TODO: I just need to create the vectors and apply them to the paths, also generate the fake bouys in an area to demonstrate the possibility

public class AreaGenerator {

    public static AisDatabaseFasade database;
    private static GeoVector vessel;
    private static int travelTime;
    private static float vesselTurnRate;

    public AreaGenerator(GeoVector vessel, int travelTime) {
        this.vessel = vessel;
        this.travelTime = travelTime;
    }

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
        AreaGenerator gen = new AreaGenerator(vessel, travelTime);
        ArrayList<GeoVector> buoys = gen.generateGrid(new Point(35, 15), new Point(37, 17));

        ArrayList<Point> result = execute(buoys);


        //create the kml

        //KMLBuilder builder = new KMLBuilder("testKML", "test for the path in a certain direction ");

        KMLBuilder builder = new KMLBuilder();
        String tag = builder.path(result, "path");
        builder.createFile(tag);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).latitude + ", " + result.get(i).longitude);
        }

    }

    static ArrayList<Point> execute(ArrayList<GeoVector> buoys) {
        ArrayList<Point> path = new ArrayList<>();
        RadiusGenerator g;

        PathPredictor predictor = new PathPredictor(vessel.location, vessel.getAngle(), vessel.getSpeed());
        path.add(new Point(vessel.location.latitude, vessel.location.longitude));
        for (int i = 0; i < travelTime; i++) {

            path.add(predictor.execute());
            vessel.location = predictor.execute();
            g = new RadiusGenerator(vessel.location.getLatitude(), vessel.location.getLongitude(), 30, 10);
            ArrayList<GeoVector> v = new ArrayList<>();
            for (int j = 0; j < buoys.size(); j++) {
                if (g.contains(g.getCircle(), buoys.get(j).location)) {
                    v.add(buoys.get(j));
                }
            }
            v = rankStations(vessel.location, v);
            vessel = vessel.addVectors(v.get(0));

            predictor = new PathPredictor(vessel.location, vessel.getAngle(), vessel.getMagnitude());
        }

        return path;
    }

    public static ArrayList<GeoVector> rankStations(Point origin, ArrayList<GeoVector> vectors) {
        Comparator<GeoVector> comp = new Comparator<GeoVector>() {
            @Override
            public int compare(GeoVector s1, GeoVector s2) {
                if (getDistance(s1.location, origin) < getDistance(s2.location, origin)) {
                    return 1;
                } else return -1;

            }
        };

        Collections.sort(vectors, comp);


        return vectors;
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

    static int getDistance(Point start, Point end) {
        int distance = (int) Math.sqrt((int) (end.latitude - start.latitude) ^ 2 + (int) (end.longitude - start.longitude) ^ 2);
        if (distance < 0) {
            distance *= -1;
        }

        return distance;
    }

    ArrayList<GeoVector> generateGrid(Point start, Point end) {
        Random rn = new Random();
        float xIncrement = (end.latitude - start.latitude) / 12;
        float yIncrement = (end.longitude - start.longitude) / 12;
        ArrayList<GeoVector> buoys = new ArrayList<>();

        for (float i = start.longitude; i <= end.longitude; i += yIncrement) {
            for (float j = start.latitude; j <= end.latitude; j += xIncrement) {
                buoys.add(new GeoVector(new Point(j, i), (float) rn.nextInt(105 - 33 + 1) + 33, rn.nextInt(360 - 0 + 1) + 0));
                System.out.println(i + ", " + j);
            }

        }

        return buoys;
    }


}

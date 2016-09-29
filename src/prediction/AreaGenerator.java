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


    private static GeoVector vessel;
    private static int travelTime;
    private float vesselTurnRate = 5f;//vesselTurnRate is set to 5 degrees.


    public AreaGenerator(GeoVector vessel, int travelTime, float vesselTurnRate) {
        this.vessel = vessel;
        this.travelTime = travelTime;
        this.vesselTurnRate = vesselTurnRate;
    }

    static ArrayList<Point> execute(ArrayList<GeoVector> buoys) {
        ArrayList<Point> path = new ArrayList<>();
        RadiusGenerator g;
        Point currentLoc;

        PathPredictor predictor = new PathPredictor(vessel.location, vessel.getAngle(), vessel.getSpeed());
        path.add(new Point(vessel.location.latitude, vessel.location.longitude));
        for (int i = 0; i < travelTime; i++) {

            currentLoc = predictor.execute();
            path.add(currentLoc);
            vessel.location = currentLoc;
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
                int angle = rn.nextInt(360 - 0 + 1) + 0;
                buoys.add(new GeoVector(new Point(j, i), (float) rn.nextInt(40 - 10 + 1) + 10, angle));
                System.out.println(i + ", " + j);
            }

        }

        return buoys;
    }

    /*
    * This method will return either the left or right bound of the vessel based upon turning speed.
    * @param isLeft -> if true, returns left bound, else returns right bound.
     */
    public float getLeftRightBounds(boolean isLeft)
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

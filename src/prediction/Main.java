package prediction;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import io.KMLBuilder;

import java.awt.geom.Area;
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
        int isOld;
        if(args.length < 4){
            isOld = 1; }
        else {
            isOld = Integer.parseInt(args[3]); }// 0 is old, 1 is new.


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

        GeoVector vessel = new GeoVector(initialCoordinates, vesselSpeed, (int) vesselCourse);
        KMLBuilder builder;
        String tag;

        if (isOld == 1) {
            //creating vessel vector
            AreaGenerator gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
            ArrayList<GeoVector> buoys = gen.generateGrid(new Point(35, 15), new Point(37, 17));

            //from left to middle
            GeoVector vesselHolder = vessel;
            ArrayList<ArrayList<Point>> results = new ArrayList<>(); //= gen.execute(buoys);
            float bound = gen.getLeftRightBounds(true);
            float angleCap = vessel.getAngle();
            for (float i = bound; i <= angleCap; i++) {
                vessel = new GeoVector(vessel.location, vesselSpeed, i);
                gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
                results.add(gen.execute(buoys));
            }

            //from middle to right
            vessel = vesselHolder;
            bound = gen.getLeftRightBounds(false);
            angleCap = vessel.getAngle();
            for (float i = angleCap; i <= bound; i++) {
                vessel = new GeoVector(vessel.location, vesselSpeed, i);
                gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
                results.add(gen.execute(buoys));
            }

            //create the kml
            builder = new KMLBuilder();
            tag = "";
            for (int i = 0; i < results.size(); i++) {
                tag += "\n" + builder.path(results.get(i), "path");
            }
        }
        else
        {
            PathPredictor path;
            float bound;
            Point newCoordinates = initialCoordinates;
            ArrayList<ArrayList<Point>> results = new ArrayList<>();
            ArrayList<Point> temp = new ArrayList<Point>();

            //For left bound
            for(int i = 0; i <= travelTime; i++)
            {
                bound = vesselTurnRate * i;
                float newAngle = vesselCourse - bound;
                if(newAngle > 360f)
                    newAngle = newAngle - 360f;
                else if(newAngle < 0f)
                    newAngle = newAngle + 360f;

                path = new PathPredictor(newCoordinates, newAngle, vesselSpeed);
                newCoordinates = path.execute();
                temp.add(newCoordinates);
            }
            results.add(temp);

            //For right bound
            temp = new ArrayList<Point>();
            newCoordinates = initialCoordinates;
            for(int i = 0; i <= travelTime; i++)
            {
                bound = vesselTurnRate * i;
                float newAngle = vesselCourse + bound;
                if(newAngle > 360f)
                    newAngle = newAngle - 360f;
                else if(newAngle < 0f)
                    newAngle = newAngle + 360f;

                path = new PathPredictor(newCoordinates, newAngle, vesselSpeed);
                newCoordinates = path.execute();
                temp.add(newCoordinates);
            }
            results.add(temp);

            //For curve
            temp = new ArrayList<Point>();
            newCoordinates = initialCoordinates;
            AreaGenerator gen = new AreaGenerator(vessel, travelTime, vesselTurnRate);
            float leftBound = gen.getLeftRightBounds(true);
            float rightBound = gen.getLeftRightBounds(false);

            for(float i = leftBound; i <= rightBound; i+=vesselTurnRate)
            {
                for(int j = 0; j < travelTime + travelTime/10; j++) {
                    path = new PathPredictor(newCoordinates, i, vesselSpeed);
                    newCoordinates = path.execute();
                }
                temp.add(newCoordinates);
                newCoordinates = initialCoordinates;
            }
            results.add(temp);


            //create the kml
            //KMLBuilder builder = new KMLBuilder("testKML", "test for the path in a certain direction ");
            builder = new KMLBuilder();
            tag = "";
            for (int i = 0; i < results.size(); i++) {
                tag += "\n" + builder.path(results.get(i), "path");
            }
        }

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
}

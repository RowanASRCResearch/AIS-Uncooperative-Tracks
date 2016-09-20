package prediction.limit;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import prediction.Controller;
import prediction.Point;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by eliakah on 9/20/16.
 */
public class AreaGenerator {
    public AisDatabaseFasade database;
    private Vector vessel;
    private int travelTime;
    private float vesselTurnRate;

    public AreaGenerator(Vector vessel, int travelTime) {
        this.vessel = vessel;
        this.travelTime = travelTime;
    }

    public void main(String args[]) {
        String mmsi = args[0];
        String date = args[1];
        int travelTime = Integer.parseInt(args[2]);

        //use fascade to pull vessel by mmsi
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(new File("src/fasade/JSONTEST.json")));
            database = new Gson().fromJson(reader, AisDatabaseFasade.class);
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }

        Point initialCoordinates = new Point(database.getLastLocation(mmsi)[0], database.getLastLocation(mmsi)[1]);
        float vesselSpeed = database.getLastSpeed(mmsi);
        float vesselCourse = database.getLastCourse(mmsi);
        vesselSize(mmsi);


        Vector vessel = new Vector(initialCoordinates, vesselSpeed, vesselCourse);
        AreaGenerator gen = new AreaGenerator(vessel, travelTime);
    }

    ArrayList<Point> execute() {
        ArrayList<Point> path = new ArrayList<>();

        for (int i = 0; i < travelTime; i++) {

        }

        return path;
    }

    Vector ApplyWeatherData() {
        Vector newVessel = null; //temporary
        //determine

        return newVessel;
    }

    /**
     * This method will determine whether or not the vessel's length is greater than or equal to 100 meters.
     * If it is, then is sets the vesselTurnRate to 3 degrees. Otherwise, vesselTurnRate is set to 5 degrees.
     *
     * @param mmsi the targeted vessel's MMSI number
     */
    private void vesselSize(String mmsi) {
        //if the total length of the vessel is greater than or equal to 100 meters
        if (database.getVesselSize(mmsi) >= 100) {
            vesselTurnRate = 3f;
            return;
        }
        vesselTurnRate = 5f;
    }


}

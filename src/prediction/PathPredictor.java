package prediction;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by eliakah on 9/14/16.
 * This class is responsible for calculating a geometric area that
 * represents possible location of a vessel based on the minutes
 * passed since the vessel experienced a loss-of-signal.
 */
public class PathPredictor {

    private float[] initialCoordinates = new float[2];
    private int travelTime;
    private float vesselSpeed;
    private float vesselCourse;
    private String lastContactTime;
    private float maxTurn = 180;
    private float vesselTurnRate;
    private ArrayList<Point> leftCoordinates = new ArrayList<>();
    private ArrayList<Point> rightCoordinates = new ArrayList<>();
    private ArrayList<Point> forwardCoordinates = new ArrayList<>();

    /**
     * Instantiates a new Area predictor.
     *
     * @param mmsi       The MMSI number of the vessel being located.
     * @param date       the date
     * @param travelTime The minutes passed since experiencing a loss-of-signal.
     */
    PathPredictor(String mmsi, String date, String travelTime, Float maxTurn) {
        this.travelTime = Integer.parseInt((travelTime));
        this.maxTurn = maxTurn;
        String[] dateSplit = Controller.database.getLastContact(mmsi);
        lastContactTime = dateSplit[1];
        initialCoordinates = Controller.database.getLastLocation(mmsi);
        vesselSpeed = Controller.database.getLastSpeed(mmsi);
        vesselCourse = Controller.database.getLastCourse(mmsi);
        vesselSize(mmsi);

        Controller.database.insertLocation(initialCoordinates[0], initialCoordinates[1]);
    }

    /**
     * Execute the predictive algorithm.
     *
     * @return the boolean flag
     */
    public boolean execute() {
//            setLeftBoundaryCoordinates();
//            setRightBoundaryCoordinates();
        populateDB();
        return true;
    }

    /**
     * Calculates the distance traveled (in kilometers)
     * in the given time (in minutes)
     *
     * @param time  Minutes the vessel has been traveling.
     * @param knots Speed the vessel in travels (in knots).
     * @return the distance
     */
    public float getDistance(int time, float knots) {

        //Converts given knots to kilometers per second.
        float knotsToKps = (knots * 0.000514444f);

        //Converts given minutes to seconds.
        float timeToSeconds = time * 60;

        //The distance traveled by the vessel, in meters.
        return (knotsToKps * timeToSeconds);
    }

    /**
     * Calculates the distance traveled (in kilometers)
     * in the given time (in minutes)
     *
     * @param knots Speed the vessel in travels (in knots).
     * @return the distance
     */
    public float getDistanceByMinute(float knots) {

        //Converts given knots to kilometers per second.
        float knotsToKps = (knots * 0.000514444f);

        //Converts given minutes to seconds.
        float timeToSeconds = 60;

        //The distance traveled by the vessel, in meters.
        return (knotsToKps * timeToSeconds);
    }


    /**
     * Populates the database with the calculated points.
     * They are added in such a way to make drawing and viewing the area easy.
     */
    public void populateDB() {
        int pointCounter = 1;
        for (Point p : leftCoordinates) {
            Controller.database.insertLocation(p.getLatitude(), p.getLongitude());
            pointCounter++;
        }

        for (Point p : forwardCoordinates) {
            Controller.database.insertLocation(p.getLatitude(), p.getLongitude());
            pointCounter++;
        }

        for (int i = rightCoordinates.size() - 1; i >= 0; i--) {
            Point p = new Point(rightCoordinates.get(i).getLatitude(), rightCoordinates.get(i).getLongitude());
            Controller.database.insertLocation(p.getLatitude(), p.getLongitude());
            pointCounter++;
        }
    }


    /**
     * Calculate coordinates based on previous latitude, longitude, heading, and distance.
     * Formula Reference: http://stackoverflow.com/questions/7222382/get-lat-long-given-current-point-distance-and-bearing
     *
     * @param lat      The latitude of the starting coordinate.
     * @param lon      The longitude of the starting coordinates.
     * @param heading  The heading of the vessel at the given coordinates.
     * @param distance The distance that will be traveled by the vessel from the initial coordinates.
     * @return destinationCoordinates   An arraylist holding the latitude and longitude of the destination coordinates
     */
    public Point calculateCoordinates(float lat, float lon, float heading, float distance) {

        //Calculates the destination coordinates given the initial coordinates, heading, and time traveled.
        float R = 6378.1f; //Radius of the Earth
        float bearing = (float) Math.toRadians(heading);

        float lat1 = (float) Math.toRadians(lat); //Current latitude point converted to radians.
        float lon1 = (float) Math.toRadians(lon); //Current longitude point converted to radians.

        float lat2 = (float) Math.asin(Math.sin(lat1) * Math.cos(distance / R) +
                Math.cos(lat1) * Math.sin(distance / R) * Math.cos(bearing));

        float lon2 = lon1 + (float) Math.atan2(Math.sin(bearing) * Math.sin(distance / R) * Math.cos(lat1),
                Math.cos(distance / R) - Math.sin(lat1) * Math.sin(lat2));

        lat2 = (float) Math.toDegrees(lat2);
        lon2 = (float) Math.toDegrees(lon2);

        Point destinationCoordinates = new Point(lat2, lon2);

        return destinationCoordinates;
    }


    /**
     * This method will determine whether or not the vessel's length is greater than or equal to 100 meters.
     * If it is, then is sets the vesselTurnRate to 3 degrees. Otherwise, vesselTurnRate is set to 5 degrees.
     *
     * @param mmsi the targeted vessel's MMSI number
     */
    void vesselSize(String mmsi) {
        //if the total length of the vessel is greater than or equal to 100 meters
        if (Controller.database.getVesselSize(mmsi) >= 100) {
            vesselTurnRate = 3f;
            return;
        }
        vesselTurnRate = 5f;
    }

}
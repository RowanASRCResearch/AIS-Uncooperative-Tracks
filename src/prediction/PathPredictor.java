package prediction;
import java.util.ArrayList;
import java.util.Collections;

/**
 * TODO: create enhenritence structure with a 'predictor' parent
 * Created by eliakah on 9/14/16.
 * This class is responsible for calculating the trajectory of a vessel
 * going at a certain speed on the ocean and show how said trajectory is
 * affected by currents and winds using data pulled from buoy stations
 */
public class PathPredictor {

    private Point initialCoordinates; //starting point
    private int travelTime; //# of minutes to run algorithm
    private float vesselSpeed; //vessel speed
    private float vesselCourse; //vessel orientation , in degrees
    private float maxTurn = 180; //maximum turn allowed
    private float vesselTurnRate = 5f;//vesselTurnRate is set to 5 degrees.


    /**
     * @param initialCoordinates
     * @param travelTime
     * @param travelTime         The minutes passed since experiencing a loss-of-signal.
     */
    PathPredictor(Point initialCoordinates, String travelTime, float vesselCourse, float vesselSpeed, Float maxTurn) {
        this.initialCoordinates = initialCoordinates;
        this.travelTime = Integer.parseInt((travelTime));
        this.maxTurn = maxTurn;
        this.vesselSpeed = vesselSpeed;
        this.vesselCourse = vesselCourse;
    }


    /**
     * Execute the predictive algorithm.
     *
     * @return the boolean flag
     */
    public boolean execute() {
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
     * in 1 minute
     *
     * @param knots Speed the vessel in travels (in knots).
     * @return the distance
     */
    public float getDistanceByMinute(float knots) {

        //Converts given knots to kilometers per second.
        float knotsToKps = (knots * 0.000514444f);

        //1 minute
        float timeToSeconds = 60;

        //The distance traveled by the vessel, in meters.
        return (knotsToKps * timeToSeconds);
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

        return (new Point(lat2, lon2));
    }
}
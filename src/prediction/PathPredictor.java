package prediction;
import java.util.ArrayList;
import java.util.Collections;

/**
 * TODO: create enhenritence structure with a 'predictor' parent
 * Created by eliakah on 9/14/16.
 * This class is responsible for calculating the trajectory of a vessel
 * going at a certain speed on the ocean and 2show how said trajectory is
 * affected by currents and winds using data pulled from buoy stations
 */
public class PathPredictor {

    public PathPredictor()
    {

    }


    /**
     * Calculates the distance traveled (in kilometers)
     * in 1 minute
     *
     * @param speed Speed the vessel in travels (in km/s).
     * @return the distance
     */
    public float getDistanceByMinute(float speed) {

        //1 minute
        float timeToSeconds = 60;

        //The distance traveled by the vessel, in meters.
        return (speed * timeToSeconds);
    }

    /**
     *
     */
    public ArrayList<Point> getPath(Point location, float speed, float angle, float timeInMinutes)
    {
        ArrayList<Point> result = new ArrayList<Point>();
        float distancePerMin = getDistanceByMinute(speed);
        Point pointHolder = location;
        for (int i = 0; i < timeInMinutes; i++) {
            result.add(pointHolder);
            pointHolder = calculateCoordinates(pointHolder.latitude, pointHolder.longitude, angle, distancePerMin);
        }

        return result;
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
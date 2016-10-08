package prediction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
     * @return the distance after one minute
     */
    public float getDistanceByMinute(float speed) {

        //1 minute
        float timeToSeconds = 60;

        //The distance traveled by the vessel, in meters.
        return (speed * timeToSeconds);
    }

    /**
     * Creates a single path of prediction based upon the provided Point location, speed, angle, time, and list of buoys.
     *
     * @param location
     * @param speed
     * @param angle
     * @param timeInMinutes
     * @param buoys
     * @return ArrayList<Point> of the path of vessel
     */
    public ArrayList<Point> getPath(Point location, float speed, float angle, float timeInMinutes, ArrayList<GeoVector> buoys)
    {
        //System.out.println("get path");
        ArrayList<Point> result = new ArrayList<Point>();
        float distancePerMin = getDistanceByMinute(speed);
        Point pointHolder = location;
        if(buoys.size() == 0) { // For calculating a path without considering Wind and Current Vectors
            System.out.println("list empty");
            for (int i = 0; i < timeInMinutes; i++) {
                result.add(pointHolder);
                pointHolder = calculateCoordinates(pointHolder.latitude, pointHolder.longitude, angle, distancePerMin);
            }
        }
        else{ // For calculating a path with Wind and Current Vectors
            System.out.println("list full");
            GeoVector vessel = new GeoVector(location, speed, (int)angle);
            result.add(pointHolder);
            for (int i = 0; i < timeInMinutes; i++) {
                buoys = rankStations(vessel.location, buoys);
                GeoVector temp = buoys.get(0);
                vessel = vessel.addVectors(temp); // Adding the wind and current vectors to the vessel vector
                distancePerMin = getDistanceByMinute(vessel.getSpeed());
                pointHolder = calculateCoordinates(pointHolder.latitude, pointHolder.longitude, vessel.getAngle(), distancePerMin);
                result.add(pointHolder);
            }
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

    /**
     * Compares the distance of the vessel with the buoys, and will sort the list of buoys based on how close they are to the vessel.
     *
     * @param origin
     * @param vectors
     * @return Sorted ArrayList<GeoVector>
     */
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
     * Returns the distance between two points
     *
     * @param start
     * @param end
     * @return int distance
     */
    private static int getDistance(Point start, Point end) {
        int distance = (int) Math.sqrt((int) (end.latitude - start.latitude) ^ 2 + (int) (end.longitude - start.longitude) ^ 2);
        if (distance < 0) {
            distance *= -1;
        }

        return distance;
    }
}
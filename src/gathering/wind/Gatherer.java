package gathering.wind;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import prediction.Point;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by eliakah on 8/18/16.
 * This class takes a point and returns a collection of bouys
 * with inside the specified radius
 */
public class Gatherer {
    HashMap<Integer, ArrayList<Object>> stations; //list of stations
    Point point; //the center point
    double radius; //radius specified in Kilometers
    Point[] circle; //collection of points making up radius
    ArrayList<Station> stList; //list holding stations inside radius


    /**
     * Constructor
     *
     * @param point
     * @param radius
     * @throws IOException
     */
    public Gatherer(Point point, float radius) throws IOException {
        this.point = point;
        this.radius = (radius * 10); //converts radius to km
        stList = new ArrayList<>();
        stations = new HashMap(); // TBA
        RadiusGenerator g = new RadiusGenerator(point.getLatitude(), point.getLongitude(), 30, radius); //generates radius
        this.circle = g.getCircle();
        AisDatabaseFasade fascade = new AisDatabaseFasade();
        JsonReader jr;
        try { //setting up fascade & biding it to ais database
            jr = new JsonReader(new FileReader(new File("src/fasade/JSONTEST.json")));
            fascade = new Gson().fromJson(jr, AisDatabaseFasade.class);
            jr.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }

        //determining boundaries to reduce search inside tables
        float latDiff = radius / 110.574f;
        float lonDiff = radius / (float) (111.320 * Math.cos(point.getLatitude() * Math.PI / 180));
        float north = point.getLatitude() + latDiff;
        float south = point.getLatitude() - latDiff;
        float east  = point.getLongitude() + lonDiff;
        float west  = point.getLongitude() - lonDiff;
        //get stations inside tables
        ArrayList<Station> tempList = fascade.getStations(north, south, east, west);

        //print points 'lat', 'lon'
        for (int i = 0; i < tempList.size(); i++) {
            if (g.contains(circle, new Point(tempList.get(i).getLat(), tempList.get(i).getLon()))) {
                stList.add(tempList.get(i));
            }
        }

        for (int i = 0; i < stList.size(); i++) {
            System.out.println(stList.get(i).getLat() + "," + stList.get(i).getLon());
        }

        /**
        ApiFascade apifascade;
        for (int i = 0; i < stList.size(); i++) {
            apifascade = new ApiFascade(stList.get(i).getId() + "", "20130808%2015:00", "20130808%2015:00");
            String text = apifascade.urlBuilder();
            String scan = apifascade.scanPage(text);
            stations.put(stList.get(i).getId(), apifascade.formatData(scan));
        }
         */


    }

    public static void main(String args[]) throws IOException {
        Point p = new Point(18.3489f, -64.8642f);
        Gatherer gatherer = new Gatherer(p, 10f);
    }

    /**
     * THis method us used to generate a random number from a range
     * This will help randomising the fake current and wind data
     */


    public int generateNumFromRange(int from, int to) {
        int genNum = 0;

        return genNum;
    }


    public HashMap<Integer, ArrayList<Object>> getStations() {


        return stations;
    }


    /**
     * station objects used to hold station information
     */


    ArrayList<Station> rankStations(Point origin, ArrayList<Station> stations) {
        Comparator<Station> comp = new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                if (s1.distance < s2.distance) {
                    return 1;
                } else return -1;

            }
        };

        Collections.sort(stations, comp);


        return stations;
    }

    private double getDistance(Point p1, Point p2) {
        //sqrt((x2-x1)^2 + (y2-y1)^2)
        double latitude = (p1.latitude - p2.latitude) * (p1.latitude - p2.latitude);
        double longitude = (p1.longitude - p2.longitude) * (p1.longitude - p2.longitude);
        return (Math.sqrt(latitude + longitude));
    }





}

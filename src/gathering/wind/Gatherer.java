package gathering.wind;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import fasade.AisDatabaseFasade;
import prediction.Point;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by eliakah on 8/18/16.
 */
public class Gatherer {
    HashMap<Integer, ArrayList<Object>> stations;
    Point point;
    int radius;
    Point[] circle;
    ArrayList<Station> stList;


    public Gatherer(Point point, int radius) throws IOException {
        this.point = point;
        this.radius = (int) (radius * 0.95);
        stList = new ArrayList<>();
        stations = new HashMap();
        RadiusGenerator g = new RadiusGenerator(point.getLatitude(), point.getLongitude(), 30, radius);
        this.circle = g.getCircle();
        AisDatabaseFasade fascade = new AisDatabaseFasade();
        JsonReader jr;
        try {
            jr = new JsonReader(new FileReader(new File("src/fasade/JSONTEST.json")));
            fascade = new Gson().fromJson(jr, AisDatabaseFasade.class);
            jr.close();
        } catch (IOException e) {
            System.out.println("File Not Found!");
        }
        float latDiff = radius / 110.574f;
        float lonDiff = radius / (float) (111.320 * Math.cos(point.getLatitude() * Math.PI / 180));
        float north = point.getLatitude() + latDiff;
        float south = point.getLatitude() - latDiff;
        float east  = point.getLongitude() + lonDiff;
        float west  = point.getLongitude() - lonDiff;
        ArrayList<Station> tempList = fascade.getStations(north, south, east, west);
        for (int i = 0; i < tempList.size(); i++) {
            if (g.contains(circle, new Point(tempList.get(i).getLat(), tempList.get(i).getLat()))) {
                stList.add(tempList.get(i));
            }
        }

        ApiFascade apifascade;
        for (int i = 0; i < stList.size(); i++) {
            apifascade = new ApiFascade(stList.get(i).getId() + "", "20130808%2015:00", "20130808%2015:00");
            String text = apifascade.urlBuilder();
            String scan = apifascade.scanPage(text);
            stations.put(stList.get(i).getId(), apifascade.formatData(scan));
        }


    }


    public HashMap<Integer, ArrayList<Object>> getStations() {


        return stations;
    }


    /**
     * station objects used to hold station information
     */
    private class station {
        String id;
        String lat;
        String lon;

        public station(String id, String lat, String lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }
}

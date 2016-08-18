package gathering;

import com.sun.xml.internal.ws.util.InjectionPlan;
import fasade.AisDatabaseFasade;
import prediction.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eliakah on 8/18/16.
 */
public class Gatherer {
    Map<Integer, Object> stations;
    Point point;
    int radius;
    Point[] circle;
    ArrayList<Station> stList;


    public Gatherer(Point point, int radius) throws IOException {
        this.point = point;
        this.radius = radius;
        stList = new ArrayList<>();
        stations = new HashMap();
        RadiusGenerator g = new RadiusGenerator(point.getLatitude(), point.getLongitude(), 30, radius);
        this.circle = g.getCircle();
        AisDatabaseFasade fascade = new AisDatabaseFasade();
        ArrayList<Station> tempList = fascade.getStations();
        for (int i = 0; i < tempList.size(); i++) {
            if (g.contains(circle, new Point(stList.get(i).getLat(), stList.get(i).getLat()))) {
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


    Map<Integer, Object> getStations() {


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

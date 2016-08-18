package gathering;

import prediction.Point;

import java.util.Map;

/**
 * Created by eliakah on 8/18/16.
 */
public class Gatherer {
    Map stations;
    Point point;
    int radius;

    public Gatherer(Point point, int radius) {

    }

    Map<Integer, Object> getStations() {
        return stations;
    }
}

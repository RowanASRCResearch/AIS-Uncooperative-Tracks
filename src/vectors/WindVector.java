package vectors;

import prediction.Point;

/**
 * Created by Research on 8/17/2016.
 */
public class WindVector extends WeatherVector {

    public WindVector(Point location, float latComponent, float longComponent) {
        super(location, latComponent, longComponent);
    }

    public String toString() {
        return "Wind Vector\n" + super.toString();
    }

}

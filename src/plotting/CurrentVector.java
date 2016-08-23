package plotting;

import prediction.Point;

/**
 * Created by Research on 8/23/2016.
 */
public class CurrentVector extends WeatherVector {

    public CurrentVector(Point location, float latComponent, float longComponent) {
        super(location, latComponent, longComponent);
    }

    public String toString() {
        return "Current Vector\n" + super.toString();
    }

}

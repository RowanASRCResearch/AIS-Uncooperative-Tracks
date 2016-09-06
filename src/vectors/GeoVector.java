package vectors;

import prediction.Point;

/**
 * Created by Research on 8/23/2016.
 */
public abstract class GeoVector {

    protected float latComponent;
    protected float longComponent;
    protected Point location;

    public GeoVector(Point location, float latComponent, float longComponent) {
        this.location = location;
        this.latComponent = latComponent;
        this.longComponent = longComponent;
    }

    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(latComponent, 2) + Math.pow(longComponent, 2));
    }

    public float getLatComponent() {
        return latComponent;
    }

    public float getLongComponent() {
        return longComponent;
    }

    public Point getLocation() {
        return location;
    }

    public String toString() {
        return location.toString() + ":\n" + Float.toString(latComponent) + "," + Float.toString(longComponent);
    }

}

package prediction.limit;

//import prediction.Point;

import prediction.Point;

/**
 * Created by nick Laposta on 8/23/2016, Modified by Sean Dale on 9/22/2016.
 */
public class GeoVector {

    public float latComponent;
    public float longComponent;

    public GeoVector(float latComponent, float longComponent) {
        this.latComponent = latComponent;
        this.longComponent = longComponent;
    }

    public GeoVector(float speed, int vectorAngle) {
        int count = 0;
        while (vectorAngle > 90) {
            vectorAngle -= 90;
            count++;
        }
        double angle = (float) (vectorAngle * (Math.PI / 180));
        float x;
        float y;
        if (count % 2 == 0) {
            x = (float) (speed * Math.sin(angle));
            y = (float) (speed * Math.cos(angle));
        } else {
            x = (float) (speed * Math.cos(angle));
            y = (float) (speed * Math.sin(angle));
        }
        if (count > 1) {
            x *= -1;
        }
        if (count % 3 != 0) {
            y *= -1;
        }
        this.latComponent = y;
        this.longComponent = x;
    }

    public GeoVector addVectors(GeoVector vec) {
        return new GeoVector((latComponent + vec.getLatComponent()), (longComponent + vec.getLongComponent()));
    }

    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(latComponent, 2) + Math.pow(longComponent, 2));
    }

    public float getAngle() {
        return (float) Math.toDegrees(Math.atan(longComponent / latComponent));
    }

    public float getLatComponent() {
        return latComponent;
    }

    public float getLongComponent() {
        return longComponent;
    }


    public String toString() {
        return Float.toString(latComponent) + "," + Float.toString(longComponent);
    }
}
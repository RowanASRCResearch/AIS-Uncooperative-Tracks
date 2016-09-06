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

    public GeoVector(Point location, float speed, int vectorAngle) {
        int count = 0;
        while(vectorAngle > 90) {
            vectorAngle -= 90;
            count++;
        }
        double angle = (float) (vectorAngle * (Math.PI / 180));
        float x;
        float y;
        if(count % 2 == 0) {
            x = (float) (speed * Math.sin(angle));
            y = (float) (speed * Math.cos( angle));
        } else {
            x = (float) (speed * Math.cos( angle));
            y = (float) (speed * Math.sin( angle));
        }
        if(count > 1) {
            x *= -1;
        }
        if(count % 3 != 0) {
            y *= -1;
        }
        this.location = location;
        this.latComponent = y;
        this.longComponent = x;
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

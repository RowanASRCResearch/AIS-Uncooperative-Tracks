package prediction;

//import prediction.Point;

/**
 * Created by nick Laposta on 8/23/2016, Modified by Sean Dale on 9/22/2016.
 */
public class GeoVector {

    public float latComponent;
    public float longComponent;
    public Point location;
    public float speed;
    public float vectorAngle = -1;

    public GeoVector(Point location, float latComponent, float longComponent) {
        this.location = location;
        this.latComponent = latComponent;
        this.longComponent = longComponent;
        this.speed = getMagnitude();
        this.vectorAngle = getAngle();
    }

    public GeoVector(Point location, float speed, int vectorAngle) {
        this.location = location;
        this.vectorAngle = vectorAngle;
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
        this.speed = speed;
        this.vectorAngle = vectorAngle;
    }

    // Vector Addition
    public GeoVector addVectors(GeoVector vec) {
        return new GeoVector(location, (latComponent + vec.getLatComponent()), (longComponent + vec.getLongComponent()));
    }

    // Gets the magnitude of the vector based upon its x and y components
    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(latComponent, 2) + Math.pow(longComponent, 2));
    }

    // Returns the angle of the vector based upon either the x and y coordinates, or just the angle if provided
    public float getAngle() {
        if(vectorAngle < 0 )
            return (float) Math.toDegrees(Math.atan(longComponent / latComponent));
        else
            return  vectorAngle;

    }


    public void setLocalAngle(float angle){
         vectorAngle = angle;
    }

    public float getLatComponent() {
        return latComponent;
    }

    public float getLongComponent() {
        return longComponent;
    }

    public float getSpeed() {
        return speed;
    }


    public String toString() {
        return Float.toString(latComponent) + "," + Float.toString(longComponent);
    }
}
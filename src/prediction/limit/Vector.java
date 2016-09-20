package prediction.limit;

import prediction.Point;

/**
 * Created by eliakah on 9/20/16.
 */
public class Vector {
    float speed;
    float direction;
    Point location;

    public Vector(Point location, float speed, float direction) {
        this.direction = direction;
        this.speed = speed;
        this.location = location;

    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }
}

package gathering.wind;

/**
 * Created by Research on 8/18/2016.
 * <p>
 * station objects used to hold station information
 */

public class Station {

    int id;
    float lat;
    float lon;

    public Station(int id, float lat, float lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
package prediction;

/**
 * Created by eliakah on 10/4/2016.
 */
public class Port {


    String name;
     String country;
        float lat;
        float lon;

        public Port(String name, String country, float lat, float lon) {
            this.name = name;
            this.country = country;
            this.lat = lat;
            this.lon = lon;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

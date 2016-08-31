package prediction;

/**
 * The type Point.
 */
public class Point {
    /**
     * The Latitude.
     */
    public float latitude,
    /**
     * The Longitude.
     */
    longitude;
    /**
     * The Description.
     */
    public String description;

    /**
     * Instantiates a new Point.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public Point(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Instantiates a new Point.
     *
     * @param latitude    the latitude
     * @param longitude   the longitude
     * @param description the description
     */
    public Point(float latitude, float longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude

     */
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public String getCoordinate() {
        return ("" + longitude + latitude);
    }

    public String toString() {
        return Float.toString(latitude) + "," + Float.toString(longitude);
    }

    public boolean equals(Object other) {
        Point otherPoint = (Point) other;
        return this.latitude == otherPoint.latitude && this.longitude == otherPoint.longitude;
    }

}

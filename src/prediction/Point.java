package prediction;

/**
 * The type Point.
 */
public class Point {
    /**
     * The Latitude.
     */
    float latitude,
    /**
     * The Longitude.
     */
    longitude;
    /**
     * The Description.
     */
    String description;

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

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets coordinate.
     *
     * @return the coordinate
     */
    public String getCoordinate() {
        return ("" + longitude + latitude);
    }
}

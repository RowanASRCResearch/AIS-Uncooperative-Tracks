package prediction;

/**
 * Created by eliakah on 9/14/16.
 * This structure holds two points , representing a path
 */
public class path {
    private Point to, from;
    private String description = "";

    public path(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public path(Point from, Point to, String description) {
        this.from = from;
        this.to = to;

        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }
}

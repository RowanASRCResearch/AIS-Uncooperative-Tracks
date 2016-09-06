package prediction.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prediction.limit.Direction;
import vectors.GeoVector;
import vectors.WindVector;
import prediction.Point;

public class Node {

    // Demo Only
    private static HashMap<Point, GeoVector> vectorMap = new HashMap<>();
    // Demo Only

    public static Direction optimizationDirection;
    public static float size;

    public Point center;
    public Point[] parameter = new Point[4]; //list of points making up the parameter
    public List vectorList;
    public Node previous;

    public Node(Point center, Node prev) {
        this.center = center;
        this.parameter[0] = new Point(center.getLatitude() + (size / 2), center.getLongitude() - (size / 2));
        this.parameter[1] = new Point(center.getLatitude() + (size / 2), center.getLongitude() + (size / 2));
        this.parameter[2] = new Point(center.getLatitude() - (size / 2), center.getLongitude() + (size / 2));
        this.parameter[3] = new Point(center.getLatitude() - (size / 2), center.getLongitude() - (size / 2));
        // Demo Only
        if(!vectorMap.keySet().contains(center)) {
            vectorMap.put(center, new WindVector(center, (float) Math.random() * 10, (float) Math.random() * 10));
        }
        // Demo Only
        vectorList = callVector();
        previous = prev;


    }

    public Node[] getNeighbors() {
        Node[] neighbors = new Node[8];
        neighbors[0] = new Node(new Point(center.getLatitude() + size, center.getLongitude() - size)
                                , this);
        neighbors[1] = new Node(new Point(center.getLatitude() + size, center.getLongitude())
                                , this);
        neighbors[2] = new Node(new Point(center.getLatitude() + size, center.getLongitude() + size)
                                , this);
        neighbors[3] = new Node(new Point(center.getLatitude(), center.getLongitude() + size)
                                , this);
        neighbors[4] = new Node(new Point(center.getLatitude() - size, center.getLongitude() + size)
                                , this);
        neighbors[5] = new Node(new Point(center.getLatitude() - size, center.getLongitude())
                                , this);
        neighbors[6] = new Node(new Point(center.getLatitude() - size, center.getLongitude() - size)
                               , this);
        neighbors[7] = new Node(new Point(center.getLatitude(), center.getLongitude() - size)
                                , this);
        return neighbors;
    }

    public float getWeight() {
        // TODO: Use optimizationDirection in calculation
        return 0;
    }

    //TODO: Get the list of vectors that would be contained in this node
    private List callVector() {
        //Demo Only
        List demo = new ArrayList();
        demo.add(vectorMap.get(center));
        vectorList = demo;
        return demo;
        //Demo Only
    }

    public boolean equals(Node other) {
        boolean flag = true;
        for(int i = 0; i < parameter.length; i++) {
            flag &= this.parameter[i] == other.parameter[i];
        }
        return flag;
    }

    public String toString() {
        return center.toString() + " : " + getWeight();
    }

}

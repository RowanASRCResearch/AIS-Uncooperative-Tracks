package dataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prediction.Point;
import prediction.PriorityBuoyList;

public class Node {

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
        vectorList = callVector();
        previous = prev;
    }

    //TODO: Make this nicer if possible
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
        // TODO
        return 0;
    }

    //TODO: Get the list of vectors that would be contained in this node
    private List callVector() {
        return null;
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

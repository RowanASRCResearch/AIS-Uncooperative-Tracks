package dataStructure;

import java.util.ArrayList;
import java.util.Collections;

import prediction.Point;
import prediction.PriorityBuoyList;

/**
 * Created by eliakah on 5/23/2016.
 * This class is the node class
 * used to hold the points
 * and nmaking up the graph
 */
public class Node {
    public Point[] points;//list of points making up the parameter
    public PriorityBuoyList buoyList;
    public ArrayList<Node> neighbors = new ArrayList<>();//neighboring nodes


    public boolean used = false;


    public Node child;

    /**
     * constructs a walkable Node with given coordinates.
     *
     * @param points    the parameter
     */
    public Node(Point[] points) {
        this.points = points;
    }

    public void setBuoyList(PriorityBuoyList buoyList) {
        this.buoyList = buoyList;
    }
    /**
     * Set neighbors.
     *
     * @param n the n
     */
    void setNeighbors(ArrayList<Node> n) {
        neighbors = n;
    }


}

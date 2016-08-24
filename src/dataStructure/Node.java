package dataStructure;

import java.util.ArrayList;
import java.util.Collections;

import prediction.Point;

/**
 * Created by eliakah on 5/23/2016.
 * This class is the node class
 * used to hold the points
 * and nmaking up the graph
 */
public class Node {
    public Point point;

    public boolean walkable;

    public Node parent = null;

    public double g; //calculated costs from start Node to this Node.

    public double h;//estimated costs to get from this Node to end Node.

    public ArrayList<Node> neighbors = new ArrayList<>();//nodes that can be used as children

    public ArrayList<Integer> x = new ArrayList<>();//nodes that can be used as children

    public ArrayList<Integer> y = new ArrayList<>();//nodes that can be used as children

    public boolean used = false;

    public Node child;

    /**
     * constructs a walkable Node with given coordinates.
     *
     * @param point    the point
     * @param walkable the walkable
     * @param g        the g
     * @param h        the h
     */
    public Node(Point point, boolean walkable, double g, double h) {
        this.point = point;
        this.point = point;
        this.walkable = walkable;
        this.g = g; //calculated costs from start Node to this Node.
        this.h = h;//estimated costs to get from this Node to end Node.
    }

    /**
     * constructs a walkable Node with given coordinates.
     *
     * @param point the point
     */
    public Node(Point point) {
        this.point = point;

    }

    /**
     * Set neighbors.
     *
     * @param n the n
     */
    void setNeighbors(ArrayList<Node> n) {
        neighbors = n;
    }

    /**
     * Set parent.
     *
     * @param p the p
     */
    void setParent(Node p) {
        parent = p;
    }

    /**
     * Sort nodes array list.
     *
     * @return the array list
     */
    ArrayList<Node> sortNodes() {
        ArrayList<Node> temp = new ArrayList<>();
        ArrayList<Double> dis = new ArrayList<>();
        for (int i = 0; i < neighbors.size(); i++) {
            dis.add(neighbors.get(i).h);
        }

        Collections.sort(dis);

        for (int i = 0; i < dis.size(); i++) {
            for (int j = 0; j < neighbors.size(); j++) {
                if (neighbors.get(j).h == dis.get(i)) {
                    temp.add(i, neighbors.get(j));
                }
            }
        }
        return temp;
    }


}

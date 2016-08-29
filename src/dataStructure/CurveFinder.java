package dataStructure;

import prediction.Point;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by Research on 8/29/2016.
 */
public class CurveFinder {

    private static PriorityQueue<Node> frontier = new PriorityQueue<>();
    private static LinkedList<Node> closed = new LinkedList<>();

    public static void main(String[] args) {

        //TODO: Can be converted to a Node count. e.g. Boat can go through 10 Nodes in the simulation time
        boolean givenTime = true;

        Point[] p = new Point[1];
        frontier.add(new Node(p, null, null));
        Node bestNode = frontier.poll();

        while(givenTime) {
            Node[] children = bestNode.getChildren();
            for(Node child: children) {
                if(isDistinct(child))
                    frontier.add(child);
            }
            bestNode = frontier.poll();
        }

    }

    private static boolean isDistinct(Node child) {
        boolean flag = false;
        for(Node n : closed)
            flag |= n.equals(child);
        for(Node n : frontier)
            flag |= n.equals(child);
        return !flag;
    }

}

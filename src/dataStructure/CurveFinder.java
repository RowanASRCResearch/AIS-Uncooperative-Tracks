package dataStructure;

import prediction.Point;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by Research on 8/29/2016.
 */
public class CurveFinder {

    private static PriorityQueue<Path> frontier = new PriorityQueue<>();
    private static LinkedList<Path> closed = new LinkedList<>();

    public static void main(String[] args) {

        //TODO: Can be converted to a Node count. e.g. Boat can go through 10 Nodes in the simulation time
        boolean givenTime = true;

        frontier.add(new Path(new Point(Float.parseFloat(args[0]), Float.parseFloat(args[1])), Float.parseFloat(args[2])));
        Path bestPath = frontier.poll();

        while(givenTime) {
            Path[] children = bestPath.getChildren();
            for(Path child: children) {
                if(isDistinct(child))
                    frontier.add(child);
            }
            bestPath = frontier.poll();
        }

    }

    private static boolean isDistinct(Path child) {
        boolean flag = false;
        for(Path p : closed)
            flag |= p.equals(child);
        for(Path p : frontier)
            flag |= p.equals(child);
        return !flag;
    }

}

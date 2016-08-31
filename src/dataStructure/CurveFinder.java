package dataStructure;

import prediction.Point;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by Nick LaPosta on 8/29/2016.
 */
public class CurveFinder {

    private final int NODE_LENGTH;

    private PriorityQueue<Path> frontier = new PriorityQueue<>();
    private LinkedList<Path> closed = new LinkedList<>();

    public CurveFinder(Point startPoint, float nodeSize) {
        frontier.add(new Path(startPoint, nodeSize));
        NODE_LENGTH = (int) (1 / nodeSize);
    }

    /**
     *
     * @param startPoint
     * @param nodeSize
     * @param time  How long the boat has been traveling in hours
     */
    public CurveFinder(Point startPoint, float nodeSize, float time) {
        frontier.add(new Path(startPoint, nodeSize));
        NODE_LENGTH = (int) (time / nodeSize);
    }

    private Path getPath(Direction dir) {
        Path bestPath = frontier.poll();

        while(bestPath.getLength() < NODE_LENGTH) {
            Path[] children = bestPath.getChildren();
            for(Path child: children) {
                if(isDistinct(child))
                    frontier.add(child);
            }
            bestPath = frontier.poll();
        }

        return bestPath;

    }

    private boolean isDistinct(Path child) {
        boolean flag = false;
        for(Path p : closed)
            flag |= p.equals(child);
        for(Path p : frontier)
            flag |= p.equals(child);
        return !flag;
    }

}

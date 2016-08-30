package dataStructure;

import prediction.Point;

/**
 * Created by Research on 8/30/2016.
 */
public class Path implements Comparable<Path> {

    private Node head;

    public Path(Node head) {
        this.head = head;
    }

    public Path(Point center, float nodeSize) {
        Node.size = nodeSize;
        //Call to get vectors
        head = new Node(center, null, null);
    }

    public Path[] getChildren() {
        Node[] nextNodes = head.getNeighbors();
        Path[] children = new Path[nextNodes.length - 1];
        for(int i = 0; i < children.length; i++) {
            if(!nextNodes[i].equals(head))
                children[i] = new Path(nextNodes[i]);
        }
        return children;
    }

    public boolean equals(Path other) {
        return this.head.equals(other.head);
    }

    public int compareTo(Path other) {
        if(this.head.getWeight() > other.head.getWeight())
            return 1;
        else if(this.head.getWeight() < other.head.getWeight())
            return -1;
        else
            return 0;
    }

}

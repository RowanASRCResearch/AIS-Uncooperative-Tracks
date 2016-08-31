package prediction.limit;

import prediction.Point;

/**
 * Created by Nick LaPosta on 8/30/2016.
 */
public class Path implements Comparable<Path> {

    private Node head;
    private int length;

    public Path(Node head, int length) {
        this.head = head;
        this.length = length;
    }

    public Path(Point center, float nodeSize) {
        Node.size = nodeSize;
        head = new Node(center, null);
        length = 1;
    }

    public Path[] getChildren() {
        Node[] nextNodes = head.getNeighbors();
        Path[] children = new Path[nextNodes.length - 1];
        for(int i = 0; i < children.length; i++) {
            if(!nextNodes[i].equals(head))
                children[i] = new Path(nextNodes[i], this.length + 1);
        }
        return children;
    }

    public int getLength() {
        return length;
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

    public String toString() {
        Node temp = head;
        String ret = temp.toString() + "\n";
        while(temp.previous != null) {
            temp = temp.previous;
            ret += temp.toString() + "\n";
        }
        return ret;
    }

}

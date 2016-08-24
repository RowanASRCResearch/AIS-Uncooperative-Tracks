package dataStructure;

/**
 * Created by Eliakah kakou
 * Map.
 * This class contains the graph where the main algorithm is ran,
 * it returns the list of Nodes making up the path
 */

import java.io.IOException;
import java.util.ArrayList;

import prediction.Point;

/**
 * The type Map.
 */
public class Map {
    /**
     * holds nodes. first dim represents x-, second y-axis.
     */
    Node[][] nodes;
    int size;

    /**
     * Instantiates a new Map.
     *
     * @param size the size
     * @throws IOException the io exception
     */
    public Map(int size) throws IOException {
        this.size = size;
        nodes = new Node[size][size];

    }


    public void insertNode(int x, int y, Node node) {

        nodes[x][y] = node;
    }


}

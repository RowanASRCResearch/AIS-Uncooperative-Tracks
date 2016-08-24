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


    /**
     * prints map to line
     * walkable node = "0",un-wakable node = "#".
     */
    public void drawMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (nodes[j][i].walkable)
                    print("0\t");
                else
                    print("#\t");
            }
            print("\n");
        }
    }

    /**
     * Draw res.
     *
     * @param pathList the path list
     */
    public void drawRes(ArrayList<Node> pathList) {
        boolean check;
        for (int i = 0; i < size; i++) {
            check = false;
            for (int j = 0; j < size; j++) {
                check = false;

                for (int k = 0; k < pathList.size(); k++) {
                    if (nodes[j][i].point.getLatitude() == pathList.get(k).point.getLatitude() && nodes[j][i].point.getLongitude() == pathList.get(k).point.getLongitude()) {
                        check = true;
                        print(">\t");
                    }
                }
                if (check == false) {
                    if (nodes[j][i].walkable)
                        print("0\t");
                    else
                        print("#\t");
                }
            }
            print("\n");
        }
    }

    /**
     * prints something to sto.
     */
    private void print(String s) {
        System.out.print(s);
    }


    /**
     * Get distance double.
     *
     * @param one the one
     * @param two the two
     * @return the double
     */
    public Double getDistance(Point one, Point two) {
        double R = 6371000; // metres
        double lat1 = (one.getLatitude() * Math.PI) / 180; // convert to toRadians
        double lat2 = (two.getLatitude() * Math.PI) / 180; // convert to toRadians
        double latDiff = ((two.getLatitude() - one.getLatitude()) * Math.PI) / 180; // convert to toRadians
        double longDiff = ((two.getLongitude() - one.getLongitude()) * Math.PI) / 180; // convert to toRadians

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(longDiff / 2) * Math.sin(longDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double d = R * c;
        return d;
    }


}

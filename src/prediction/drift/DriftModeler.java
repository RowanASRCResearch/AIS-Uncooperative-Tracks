package prediction.drift;

import prediction.Point;
import prediction.path.Node;
import prediction.path.Path;
import vectors.VesselVector;

import java.util.InputMismatchException;
import java.util.LinkedList;

/**
 * Created by Nick LaPosta on 9/6/2016.
 */
public class DriftModeler {

    public static void main(String[] args) {

        LinkedList<Point> track = new LinkedList<>();

        Point start = new Point(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
        double vesselSpeed = Double.parseDouble(args[2]);
        int vesselDirection = Integer.parseInt(args[3]);

        VesselVector vessel = new VesselVector(start, vesselSpeed, vesselDirection);

        int stepSize = Integer.parseInt(args[5]); // In seconds
        int maxSteps= Integer.parseInt(args[4]) * 60 / stepSize; // Argument in minutes

        Path path = new Path(start, Integer.parseInt(args[6]));

        int step = 0;
        do {

            track.add(vessel.getLocation());

            if(!path.getHead().contains(vessel.getLocation())) {
                Node[] neighbors = path.getHead().getNeighbors();
                int index = 0;
                while(!neighbors[index].contains(vessel.getLocation()))
                    index++;
                try {
                    path.append(neighbors[index]);
                } catch(IndexOutOfBoundsException ie) {
                    Node restart = new Node(vessel.getLocation(), path.getHead());
                    path.append(restart);
                }
            }

            // Get relevant factors to vessel drift

            // Simulate vessel movement
            vessel.simulate(stepSize, windResult, currentResult);

            step++;

        } while(step <= maxSteps);

    }

}

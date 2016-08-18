package plotting;

import prediction.Point;
import gathering.Gatherer;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * @author: Nick LaPosta
 * Date Created: 8/15/2016.
 */
public class WindPlotter {

    private static Point center;
    private static int radius;
    private static PlottingWindow window;

    public static void main(String[] args) throws IOException {

        center = new Point(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
        radius = Integer.parseInt(args[2]);

        Gatherer gatherer = new Gatherer(center, radius);
        HashMap<Integer,ArrayList<Object>> windData = gatherer.getStations();
        //HashMap windData = placeholderFunction(center, radius);

        ArrayList<WindVector> vectors = new ArrayList<>();

        Set<Integer> buoyIDs = windData.keySet();
        WindVector vectorExample = null;
        for(Integer id : buoyIDs) {
            ArrayList<Object> buoyData = (ArrayList) windData.get(id);
            vectors.add(convertVector(  (Point) buoyData.get(0),
                            (float) buoyData.get(1),
                            (float) buoyData.get(2)));
        }

        createFrame();
        addVectors(vectors);

    }

    private static void createFrame() {
        JFrame frame = new JFrame("Test");
        window = new PlottingWindow(center, radius);
        frame.add(window);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void addVector(WindVector vector) {
        window.plotVector(vector);
    }

    private static void addVectors(Collection<WindVector> collection) {window.plotVectors(collection);}

    private static WindVector convertVector(Point location, float windSpeed, float windAngle) {
        int count = 0;
        while(windAngle > 90) {
            windAngle -= 90;
            count++;
        }
        windAngle = (float) (windAngle * (Math.PI / 180));
        float x;
        float y;
        if(count % 2 == 0) {
            x = (float) (windSpeed * Math.sin((double) windAngle));
            y = (float) (windSpeed * Math.cos((double) windAngle));
        } else {
            x = (float) (windSpeed * Math.cos((double) windAngle));
            y = (float) (windSpeed * Math.sin((double) windAngle));
        }
        if(count > 1) {
            x *= -1;
        }
        if(count % 3 != 0) {
            y *= -1;
        }
        return new WindVector(location, y, x);
    }

    /*private static HashMap<Integer,ArrayList<Object>> placeholderFunction(Point p, float radius) {
        HashMap map = new HashMap<>();
        ArrayList list = new ArrayList<>();
        list.add(new Point((float) 77.919191, (float)-84.869696));
        list.add(new Float(4.33));
        list.add(new Integer(262));
        map.put(74, list);
        return map;
    }*/

}

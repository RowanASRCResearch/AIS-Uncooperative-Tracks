package plotting;

import prediction.Point;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author: Nick LaPosta
 * Date Created: 8/15/2016.
 */
public class WindPlotter {

    public static void main(String[] args) {

        Point center = new Point(Float.parseFloat(args[0]), Float.parseFloat(args[1]));
        float radius = Float.parseFloat(args[2]);

        HashMap windData = placeholderFunction(center, radius);
        Set<Integer> buoyIDs = windData.keySet();
        WindVector vectorExample = null;
        for(Integer id : buoyIDs) {
            ArrayList<Object> buoyData = (ArrayList) windData.get(id);
            Point location = (Point) buoyData.get(0);
            double windSpeed = (double) buoyData.get(1);
            double windAngle = (int) buoyData.get(2);
            int count = 0;
            while(windAngle > 90) {
                windAngle -= 90;
                count++;
            }
            windAngle = windAngle * (Math.PI / 180);
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
            vectorExample = new WindVector(location, y, x);
            System.out.println(location + " : " + vectorExample);
        }

        JFrame frame = new JFrame("Test");
        PlottingWindow window = new PlottingWindow(center, radius);
        frame.add(window);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.plotVector(vectorExample);
        window.plotVector(new WindVector(new Point(77.6f, -85.4f), 1.2f, 0.86f));
    }

    private static HashMap<Integer,ArrayList<Object>> placeholderFunction(Point p, float radius) {
        HashMap map = new HashMap<>();
        ArrayList list = new ArrayList<>();
        list.add(new Point((float) 77.919191, (float)-84.869696));
        list.add(new Double(4.33));
        list.add(new Integer(262));
        map.put(74, list);
        return map;
    }

}

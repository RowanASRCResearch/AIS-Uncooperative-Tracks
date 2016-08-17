package plotting;

import prediction.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @author: Nick LaPosta
 * Date Created: 8/15/2016.
 */
public class WindPlotter {

    public static void main(String[] args) {

        HashMap windData = placeholderFunction( new Point(Float.parseFloat(args[0]), Float.parseFloat(args[1])),
                                                Float.parseFloat(args[2]));
        Set<Integer> buoyIDs = windData.keySet();
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
            double x;
            double y;
            if(count % 2 == 0) {
                x = windSpeed * Math.sin(windAngle);
                y = windSpeed * Math.cos(windAngle);
            } else {
                x = windSpeed * Math.cos(windAngle);
                y = windSpeed * Math.sin(windAngle);
            }
            if(count > 1) {
                x *= -1;
            }
            if(count % 3 != 0) {
                y *= -1;
            }
            System.out.println(location + " : " + x + "," + y);
        }

    }

    private static HashMap<Integer,ArrayList<Object>> placeholderFunction(Point p, float radius) {
        HashMap map = new HashMap<>();
        ArrayList list = new ArrayList<>();
        list.add(new Point((float) 77.919191, (float)-84.969696));
        list.add(new Double(4.33));
        list.add(new Integer(262));
        map.put(74, list);
        return map;
    }

}

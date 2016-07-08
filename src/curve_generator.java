
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author martijn and Bob
 */
public class curve_generator
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        PointF start = new PointF(-100f, 0.0f);
        PointF dest = new PointF(-50f, 35f);
        PointF end = new PointF(-110f, 50.0f);
        PointF end2 = findReflected(end, start, dest);
        float radius = 30f;
        float period = 3f;

        /*
        THIS IS OF MOST CONCERN

        start is start point
        end  is end point
        radius is float of degrees of flex
        the first boolean represents the choice of shortest curve or longest
        next boolean represents the side of inflection false is up ward curving, true is downward
        */
        List<PointF> points = generateCurve(start, end, radius, period, true, false);

        System.out.println(points);

        //UNCOMMENT THE FOLLOWING TO YIELD IMAGE
        // Calculate the bounds of the curve
        Rectangle2D.Float bounds = new Rectangle2D.Float(points.get(0).x, points.get(0).y, 0, 0);
        for (int i = 1; i < points.size(); ++i) {
            bounds.add(points.get(i).x, points.get(i).y);
        }
        bounds.add(start.x, start.y);
        bounds.add(end.x, end.y);

        BufferedImage img = new BufferedImage((int) (bounds.width - bounds.x + 50), (int) (bounds.height - bounds.y + 50), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.translate(25.0f - bounds.getX(), 25.0f - bounds.getY());
        g.setStroke(new BasicStroke(1.0f));


        g.setColor(Color.DARK_GRAY);
        g.drawLine(-1000, 0, 1000, 0);
        g.drawLine(0, -1000, 0, 1000);

        g.setColor(Color.RED);
        for (int i = 0; i < points.size(); ++i) {
            if (i > 0) {
                Line2D.Float f = new Line2D.Float(points.get(i - 1).x, points.get(i - 1).y, points.get(i).x, points.get(i).y);
                System.out.println("Dist : " + f.getP1().distance(f.getP2()));
                g.draw(f);
            }

            g.fill(new Ellipse2D.Float(points.get(i).x - 0.8f, points.get(i).y - 0.8f, 1.6f, 1.6f));

        }

        g.setColor(Color.GREEN);
        Line2D.Float f = new Line2D.Float(start.x, start.y, dest.x, dest.y);
        System.out.println("Dist : " + f.getP1().distance(f.getP2()));
        g.draw(f);

        g.setColor(Color.BLUE);
        g.fill(new Ellipse2D.Float(start.x - 1, start.y - 1, 3, 3));
        g.fill(new Ellipse2D.Float(end.x - 1, end.y - 1, 3, 3));
        g.fill(new Ellipse2D.Float(dest.x - 1, dest.y -1, 3,3));


        g.dispose();

        ImageIO.write(img, "PNG", new File("result.png"));
    }

    static PointF findReflected(PointF focus, PointF start, PointF end) {
        float x;
        float y;

        //first find eq of line start to end


        return new PointF(x, y);
    }

    static class PointF
    {

        public float x, y;

        public PointF(float x, float y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString()
        {
            return "(" + x + "," + y + ")";
        }
    }

    private static List<PointF> generateCurve(PointF start, PointF end, float radius, float min_dist, boolean shortest, boolean side)
    {

        List<PointF> pOutPut = new ArrayList<PointF>();

        // Calculate the middle of the two given points.
        PointF mPoint = new PointF(start.x + end.x, start.y + end.y);
        mPoint.x /= 2.0f;
        mPoint.y /= 2.0f;
        System.out.println("Middle Between From and To = " + mPoint);


        // Calculate the distance between the two points
        float xDiff = end.x - start.x;
        float yDiff = end.y - start.y;
        float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        System.out.println("Distance between From and To = " + distance);

        if (radius * 2.0f < distance) {
            throw new IllegalArgumentException("The radius is too small! The given points wont fall on the circle.");
        }

        // Calculate the middle of the expected curve.
        float factor = (float) Math.sqrt((radius * radius) / ((end.x - start.x) * (end.x - start.x) + (end.y - start.y) * (end.y - start.y)) - 0.25f);
        PointF circleMiddlePoint = new PointF(0, 0);
        if (side) {
            circleMiddlePoint.x = 0.5f * (start.x + end.x) + factor * (end.y - start.y);
            circleMiddlePoint.y = 0.5f * (start.y + end.y) + factor * (start.x - end.x);
        } else {
            circleMiddlePoint.x = 0.5f * (start.x + end.x) - factor * (end.y - start.y);
            circleMiddlePoint.y = 0.5f * (start.y + end.y) - factor * (start.x - end.x);
        }
        System.out.println("Middle = " + circleMiddlePoint);

        // Calculate the two reference angles
        float angle1 = (float) Math.atan2(start.y - circleMiddlePoint.y, start.x - circleMiddlePoint.x);
        float angle2 = (float) Math.atan2(end.y - circleMiddlePoint.y, end.x - circleMiddlePoint.x);

        // Calculate the step.
        float step = min_dist / radius;
        System.out.println("Step = " + step);

        // Swap them if needed
        if (angle1 > angle2) {
            float temp = angle1;
            angle1 = angle2;
            angle2 = temp;

        }
        boolean flipped = false;
        if (!shortest) {
            if (angle2 - angle1 < Math.PI) {
                float temp = angle1;
                angle1 = angle2;
                angle2 = temp;
                angle2 += Math.PI * 2.0f;
                flipped = true;
            }
        }
        for (float f = angle1; f < angle2; f += step) {
            PointF p = new PointF((float) Math.cos(f) * radius + circleMiddlePoint.x, (float) Math.sin(f) * radius + circleMiddlePoint.y);
            pOutPut.add(p);
        }
        if (flipped ^ side) {
            pOutPut.add(start);
        } else {
            pOutPut.add(end);
        }

        return pOutPut;
    }
}
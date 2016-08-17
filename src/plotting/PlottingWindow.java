package plotting;

import prediction.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Research on 8/17/2016.
 */
public class PlottingWindow extends JPanel {

    private Point center;
    private float radius;
    private List<WindVector> vectorQueue;

    public PlottingWindow(Point center, float radius) {
        this.center = center;
        this.radius = radius;
        vectorQueue = new ArrayList<>();
    }

    public void plotVector(WindVector v) {
        vectorQueue.add(v);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillOval((this.getWidth() / 2) - 2, (this.getHeight() / 2) - 2, 4, 4);
        g.drawOval(0, 0, this.getWidth(), this.getHeight());
        for(WindVector v : vectorQueue) {
            drawVector(v, g);
        }
    }

    private void drawVector(WindVector v, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        float[] dash = {2f, 0f, 2f};
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        int[] pixelLocation = pointToPixel(v.getLocation());
        g2d.drawLine(pixelLocation[0], pixelLocation[1], pixelLocation[0], pixelLocation[1] + (int) (v.getLongComponent() * 20));
        g2d.drawLine(pixelLocation[0], pixelLocation[1], pixelLocation[0] + (int) (v.getLatComponent() * 20), pixelLocation[1]);
    }

    private int[] pointToPixel(Point p) {
        int[] ret = {(int) (Math.random() * this.getWidth()), (int) (Math.random() * this.getHeight())};
        return ret;
    }

}

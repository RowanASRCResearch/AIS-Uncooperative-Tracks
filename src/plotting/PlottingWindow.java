package plotting;

import prediction.Point;
import vectors.WeatherVector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Research on 8/17/2016.
 */
public class PlottingWindow extends JPanel {

    private Point center;
    private float radius;
    private float east;
    private float west;
    private float north;
    private float south;
    private List<WeatherVector> vectorQueue;

    public PlottingWindow(Point center, float radius) {
        this.center = center;
        this.radius = radius;
        float latDiff = radius / 110.574f;
        float lonDiff = radius / (float) (111.320 * Math.cos(center.getLatitude() * Math.PI / 180));
        north = center.getLatitude() + latDiff;
        south = center.getLatitude() - latDiff;
        east  = center.getLongitude() + lonDiff;
        west  = center.getLongitude() - lonDiff;
        vectorQueue = new ArrayList<>();
    }

    public void plotVector(WeatherVector v) {
        vectorQueue.add(v);
        repaint();
    }

    public void plotVectors(Collection<WeatherVector> vectors) {
        vectorQueue.addAll(vectors);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillOval((this.getWidth() / 2) - 2, (this.getHeight() / 2) - 2, 4, 4);
        g.drawOval(0, 0, this.getWidth(), this.getHeight());
        for(WeatherVector v : vectorQueue) {
            drawVector(v, g);
        }
    }

    private void drawVector(WeatherVector v, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        float[] dash = {2f, 0f, 2f};
        BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        int[] pixelLocation = pointToPixel(v.getLocation());
        g2d.setColor(Color.BLACK);
        g2d.drawLine(pixelLocation[0], pixelLocation[1], pixelLocation[0], pixelLocation[1] + (int) (v.getLongComponent() * 10));
        g2d.drawLine(pixelLocation[0], pixelLocation[1], pixelLocation[0] + (int) (v.getLatComponent() * 10), pixelLocation[1]);
        g2d.setColor(Color.RED);
        g2d.fillOval(pixelLocation[0] - 2, pixelLocation[1] - 2, 4, 4);
    }

    private int[] pointToPixel(Point p) {
        int width  = (int) (((p.getLongitude() - west) / (east - west)) * this.getWidth());
        int height = (int) (((p.getLatitude() - south) / (north - south)) * this.getHeight());

        int[] ret = {width, this.getHeight() - height};
        return ret;
    }

}

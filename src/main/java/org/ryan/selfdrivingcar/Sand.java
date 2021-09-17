package org.ryan.selfdrivingcar;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Model and view
 */
public class Sand {
    int width;
    int height;
    Canvas canvas;
    GraphicsContext gc;
    boolean[][] sand;

    public Sand(Canvas canvas)
    {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        this.width = (int)canvas.getWidth();
        this.height = (int)canvas.getHeight();
        this.sand = new boolean[width][height];
    }

    public void addSandPixel(int x, int y)
    {
        sand[x][y] = true;
        gc.getPixelWriter().setColor(x, y, Color.YELLOW);
    }

    public void addSandCircle(int centerX, int centerY, int radius)
    {
//        gc.setFill(Color.YELLOW);
//        gc.fillOval(centerX - radius, centerY - radius, radius*2, radius*2);
        List<Point2D> pointsInCircle = getPointsInCircle(centerX, centerY, radius);
        for (Point2D p : pointsInCircle) {
            addSandPixel((int)p.getX(), (int)p.getY());
        }
    }

    private List<Point2D> getPointsInCircle(int centerX, int centerY, int radius)
    {
        ArrayList<Point2D> ret = new ArrayList<>();

        // bounding box
        int minX = centerX - radius;
        int maxX = centerX + radius;
        int minY = centerY - radius;
        int maxY = centerY + radius;

        Point2D center = new Point2D(centerX, centerY);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (Math.abs(center.distance(x, y)) <= radius) {
                    // i don't love having sand-specific logic in here, but it seems more efficient than filtering later
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        Point2D p = new Point2D(x, y);
                        ret.add(p);
                    }
                }
            }
        }

        return ret;
    }
}

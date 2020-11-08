package shapes;

import rayTracing.Intersection;

import java.awt.*;

public class Plane extends Shape{
    public Plane(Point pos, Point rot, float scale, Color color) {
        super(pos, rot, scale, color);
    }

    @Override
    public Point[] getPoints() {
        return new Point[0];
    }

    @Override
    public Intersection collisionTest(Ray ray) {
        return null;
    }
}

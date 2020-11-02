package shapes;

import rayTracing.Intersection;

import java.awt.*;

public class Lamp extends Shape{

    public Lamp(Point pos, float intensity) {
        super(pos, new Point(), intensity, Color.WHITE);
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

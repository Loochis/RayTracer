package shapes;

import rayTracing.Intersection;

import java.awt.*;

public class Lamp extends Shape{

    private Point pos;
    public Lamp(Point pos, float intensity) {
        super(pos, new Point(), intensity, Color.WHITE);
        this.pos = pos;
    }

    @Override
    public Point getPos() {
        return pos;
    }

    @Override
    public void setPos(Point coord) {
        pos = coord;
    }

    @Override
    public Point[] getPoints() {
        return new Point[] {pos};
    }

    @Override
    public Intersection collisionTest(Ray ray) {
        return null;
    }
}

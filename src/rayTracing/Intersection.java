package rayTracing;

import shapes.Point;
import shapes.Shape;

import java.awt.*;

public class Intersection {
    private Point pos1, pos2, normal;
    private Color color;
    private float zDepth;
    private shapes.Shape collider;

    public Intersection(Point pos1, Point pos2, Point normal, Color color, float zDepth, shapes.Shape collider) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.normal = normal;
        this.color = color;
        this.zDepth = zDepth;
        this.collider = collider;
    }

    public Point getPos1() {
        return pos1;
    }

    public void setPos1(Point pos1) {
        this.pos1 = pos1;
    }

    public Point getPos2() {
        return pos2;
    }

    public void setPos2(Point pos2) {
        this.pos2 = pos2;
    }

    public Point getNormal() {
        return normal;
    }

    public void setNormal(Point normal) {
        this.normal = normal;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getzDepth() {
        return zDepth;
    }

    public void setzDepth(float zDepth) {
        this.zDepth = zDepth;
    }

    public Shape getShape() {
        return collider;
    }

    public void setShape(shapes.Shape collider) {
        this.collider = collider;
    }

}

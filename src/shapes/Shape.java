package shapes;

import LoochisMath.PointMath;
import LoochisMath.VectorMath;

import java.awt.*;

public abstract class Shape {
    private Point pos, rot; // Position / Rotation of the shape
    private float scale; // Scale of the shape
    private Color color; // Color of the shape

    public Shape(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Updates the average point (center) of the shape
     */
    private void updatePos() {
        pos = PointMath.averagePoints(getPoints());
    }

    /**
     * Updates the rotation of the shape
     */
    private void updateRot() {
    }


    public double getScale() {
        return scale;
    }                // Getter for scale

    public void setScale(float scale) {
        this.scale = scale;
    } // Setter for scale

    public Point getRot() {
        return rot;
    }

    public void setRot(Point rot) {
        this.rot = rot;
    }

    public void rotate(Point rot) {
        this.rot = VectorMath.Add(this.rot, rot);
    }


    /**
     * Gets the verticies of the shape
     *
     * @return the verticies that make up the shape
     */
    public abstract Point[] getPoints();

    /**
     * Sets the position of the center of the shape
     *
     * @param coords the new coordinates of the center of the shape
     */
    public void setPos(Point coords) {
        Point[] points = getPoints();
        for (Point point : points) {
            Point difference = VectorMath.Subtract(point, pos); // Get the point in local-space
            point = VectorMath.Add(difference, coords);         // Move the point in world-space
        }
        updatePos();
    }

    /**
     * Translates the shape by the specified amount
     *
     * @param coords the coordinates to translate the shape by
     */
    public void translate(Point coords) {
        Point[] points = getPoints();
        for (Point point : points)
            point = VectorMath.Add(point, coords);
        updatePos();
    }

    /**
     * Tests for a collision between a ray and the shape
     *
     * @param ray the ray to test collision with
     * @return the point(s) of intersection, null if none
     * index in order from closest to furthest from ray origin
     */
    public abstract Point[] collisionTest(Ray ray);
}

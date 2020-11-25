package shapes;

import LoochisMath.PointMath;
import LoochisMath.VectorMath;
import rayTracing.Intersection;

import java.awt.*;

public abstract class Shape {

    private Point pos, rot; // Position / Rotation of the shape
    private float scale; // Scale of the shape
    private Color color; // Color of the shape
    private double glossy;

    public double getGlossy() {
        return glossy;
    }

    public void setGlossy(double glossy) {
        this.glossy = glossy;
    }

    public Shape(Point pos, Point rot, float scale, Color color) {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.color = color;
    }

    // --- POSITION MODIFIERS --- // -----------------------------------------------------------------------------------

    /**
     * Gets the position of the shape
     * @return the position of the shape (Point)
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Called by subclasses to update the position variable of the shape
     * @param pos the new position
     */
    protected void updatePos(Point pos) {
        this.pos = pos;
    }

    /**
     * Sets the position of the shape
     * @param coords the position to set the shape to
     */
    public void setPos(Point coords) {
        Translate(VectorMath.Subtract(coords, pos));
        pos = coords;
    }

    /**
     * Translates the shape by the specified amount
     * @param coords the coordinates to translate the shape by
     */
    public abstract void Translate(Point coords);

    // --- SCALE MODIFIERS --- // --------------------------------------------------------------------------------------

    /**
     * Gets the scale of the object
     * @return the scale variable of the shape
     */
    public float getScale() {
        return scale;
    }

    /**
     * Called by subclasses to update the scale variable of the shape
     * @param scale the new scale
     */
    protected void updateScale(float scale) {
        this.scale = scale;
    }

    /**
     * Sets the scale of the shape
     * @param scale the new scale the shape will be
     */
    public void setScale(float scale) {
        Scale(scale - this.scale);
        this.scale = scale;
    }

    /**
     * Scale the shape by the specified amount
     * @param scale the amount to scale by
     */
    public abstract void Scale(float scale);

    // --- ROTATION MODIFIERS --- // -----------------------------------------------------------------------------------

    /**
     * Gets the rotation of the object
     * @return the rotation of the object (Point)
     */
    public Point getRot() {
        return rot;
    }

    /**
     * Called by subclasses to update the rotation variable of the shape
     * @param rot the updated rotation
     */
    protected void updateRotation(Point rot) {
        this.rot = rot;
    }

    /**
     * Sets the rotation of the shape
     * @param rot the rotation of the shape
     */
    public void setRot(Point rot) {
        Rotate(VectorMath.Subtract(rot, this.rot));
        this.rot = rot;
    }

    /**
     * Rotates the shape by the specified amount
     * @param rot the amount to rotate by
     */
    public abstract void Rotate(Point rot);

    // --- GENERAL GETTERS AND SETTERS --- // --------------------------------------------------------------------------

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the verticies of the shape
     *
     * @return the verticies that make up the shape
     */
    public abstract Point[] getPoints();

    /**
     * Tests for a collision between a ray and the shape
     *
     * @param ray the ray to test collision with
     * @return the point(s) of intersection, null if none
     * index in order from closest to furthest from ray origin
     */
    public abstract Intersection collisionTest(Ray ray);
}

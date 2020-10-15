package shapes;

import java.awt.*;

public class Sphere {
    private Point pos;
    private float radius;
    private Color color;

    //--- Constructors ---//

    /**
     * Default constructor for sphere
     */
    public Sphere() {
        pos = new Point();
        radius = 1;
        color = Color.WHITE;
    }

    /**
     * constructor with arguments
     *
     * @param pos    position of the center of the sphere
     * @param radius radius of the sphere
     * @param color  color of the sphere
     */
    public Sphere(Point pos, int radius, Color color) {
        this.pos = pos;
        this.radius = radius;
        this.color = color;
    }

    //--- Getters and setters ---//

    /**
     * gets the color of the sphere
     * @return the color of the sphere
     */
    public Color getCol() {
        return color;
    }

    /**
     * sets the color of the sphere
     * @param newColor the color to change the sphere to
     */
    public void setCol(Color newColor) {
        color = newColor;
    }

    /**
     * gets the position of the sphere
     * @return the (Point) position of the sphere
     */
    public Point getPos() {
        return pos;
    }

    /**
     * sets the position of the sphere
     * @param newPos the (Point) position to move the sphere to
     */
    public void setPos(Point newPos) {
        pos = newPos;
    }

    /**
     * gets the radius of the sphere
     * @return the radius of the sphere
     */
    public float getRadius() {
        return radius;
    }

    /**
     * sets the color of the sphere
     * @param newRadius the color to change the sphere to
     */
    public void setRadius(float newRadius) {
        radius = newRadius;
    }
}

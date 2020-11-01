package shapes;

import LoochisMath.VectorMath;

import java.awt.*;

public class Sphere extends Shape{
    private Point pos;
    private float radius;

    //--- Constructors ---//

    /**
     * Default constructor for sphere
     */
    public Sphere() {
        super(Color.WHITE);
        pos = new Point();
        radius = 1;
    }

    /**
     * constructor with arguments
     *
     * @param pos    position of the center of the sphere
     * @param radius radius of the sphere
     * @param color  color of the sphere
     */
    public Sphere(Point pos, int radius, Color color) {
        super(color);
        this.pos = pos;
        this.radius = radius;
    }

    //--- Getters and setters ---//

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


    @Override
    public Point[] getPoints() {
        return new Point[] {pos};
    }

    @Override
    public Point[] collisionTest(Ray ray) {
        ray = ray.Normalized();
        float t = VectorMath.Dot(VectorMath.Subtract(pos, ray.getOrigin()), ray.getHead()); // Move position to 0,0 relative to ray. Get dot
        Point p = VectorMath.Add(VectorMath.Multiply(ray.getHead(), t), ray.getOrigin()); // Find point on perpendicular plane, translate back to world space
        float y = VectorMath.Length2(VectorMath.Subtract(pos, p)); // Distance from the center of the sphere
        if (y > Math.pow(radius, 2)) // If the distance is greater than the radius, return no intersections
            return null;

        float x = (float) Math.sqrt(Math.pow(radius, 2) - y); // Distance from plane intersection to spheres surface
        Point[] out = new Point[2];
        out[0] = VectorMath.Multiply(ray.getHead(), t-x);
        out[1] = VectorMath.Multiply(ray.getHead(), t+x);

        return out;
    }
}

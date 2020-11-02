package shapes;

import LoochisMath.VectorMath;
import rayTracing.Intersection;

import java.awt.*;

public class Sphere extends Shape{

    //--- Constructors ---//

    /**
     * Default constructor for sphere
     */
    public Sphere() {
        super(Color.WHITE);
    }

    /**
     * constructor with arguments
     *
     * @param pos    position of the center of the sphere
     * @param radius radius of the sphere
     * @param color  color of the sphere
     */
    public Sphere(Point pos, int radius, Color color) {
        super(pos, new Point(), radius, color);
    }

    //--- Getters and setters ---//

    /**
     * gets the position of the sphere
     * @return the (Point) position of the sphere
     */
    public Point getPos() {
        return super.getPos();
    }

    /**
     * sets the position of the sphere
     * @param newPos the (Point) position to move the sphere to
     */
    public void setPos(Point newPos) {
        super.setPos(newPos);
    }


    @Override
    public Point[] getPoints() {
        return new Point[] {super.getPos()};
    }

    @Override
    public Intersection collisionTest(Ray ray) {
        ray = ray.Normalized();
        float t = VectorMath.Dot(VectorMath.Subtract(super.getPos(), ray.getOrigin()), ray.getHead()); // Move position to 0,0 relative to ray. Get dot
        Point p = VectorMath.Add(VectorMath.Multiply(ray.getHead(), t), ray.getOrigin()); // Find point on perpendicular plane, translate back to world space
        float y = VectorMath.Length2(VectorMath.Subtract(super.getPos(), p)); // Distance from the center of the sphere
        if (y > Math.pow(super.getScale(), 2)) // If the distance is greater than the radius, return no intersections
            return null;

        float x = (float) Math.sqrt(Math.pow(super.getScale(), 2) - y); // Distance from plane intersection to spheres surface
        Point p1 = VectorMath.Add(VectorMath.Multiply(ray.getHead(), t-x), ray.getOrigin());
        Point p2 = VectorMath.Add(VectorMath.Multiply(ray.getHead(), t+x), ray.getOrigin());

        return new Intersection(p1, p2, VectorMath.Normalize(VectorMath.Subtract(p1, super.getPos())), getColor(), t-x);
    }
}

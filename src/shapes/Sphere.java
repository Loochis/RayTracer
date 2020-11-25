package shapes;

import LoochisMath.VectorMath;
import rayTracing.Intersection;

import java.awt.*;
import java.util.Random;

public class Sphere extends Shape{

    private float roughness = 0;

    public float getRoughness() {
        return roughness;
    }

    public void setRoughness(float roughness) {
        this.roughness = roughness;
    }

    //--- Constructors ---//

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

    @Override
    public void Translate(Point coords) {
        super.updatePos(VectorMath.Add(super.getPos(), coords));
    }

    @Override
    public void Scale(float scale) {
        super.updateScale(super.getScale() + scale);
    }

    @Override
    public void Rotate(Point rot) {

    } // Try to rotate a perfect sphere :/


    @Override
    public Point[] getPoints() {
        return new Point[] {super.getPos()};
    }

    public boolean IsIntersecting(Sphere sphere2) {
        Point difference = VectorMath.Subtract(getPos(), sphere2.getPos());
        float dist = VectorMath.Length2(difference);
        if (dist < Math.pow(getScale() + sphere2.getScale(), 2))
            return true;
        return false;
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

        Random rand = new Random();
        rand.setSeed((int) (p1.getX() * p1.getY() * p1.getZ())); // Base random on position
        Point norm = VectorMath.Normalize(VectorMath.Subtract(p1, super.getPos()));
        norm.setX((float) (norm.getX() + (rand.nextFloat() - 0.5) * roughness));
        norm.setY((float) (norm.getY() + (rand.nextFloat() - 0.5) * roughness));
        norm.setZ((float) (norm.getZ() + (rand.nextFloat() - 0.5) * roughness));
        return new Intersection(p1, p2, norm, getColor(), t-x, this);
    }
}

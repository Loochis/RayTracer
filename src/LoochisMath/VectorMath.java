package LoochisMath;

import shapes.Point;
import shapes.Ray;

public class VectorMath {

    /**
     * Adds two points
     * @param A point 1 to add
     * @param B point 2 to add
     * @return the sum of the components of the two points
     */
    public static Point Add(Point A, Point B) {
        float[] Acomps = A.getPos();
        float[] Bcomps = B.getPos();

        for (int i = 0; i < 3; i++)
            Acomps[i] += Bcomps[i]; // Add the comps and store in A (not a reference to original point)

        return new Point(Acomps);
    }

    /**
     * subtracts one vector from another
     * @param A the vector to subtract from
     * @param B the vector to subtract
     * @return the difference of the two vectors
     */
    public static Point Subtract(Point A, Point B) {
        return new Point(A.getX() - B.getX(), A.getY() - B.getY(), A.getZ() - B.getZ());
    }

    /**
     * Multiplies a vectors components by the specified amount
     * @param A the vector to multiply
     * @param multiplier the amount to multiply by
     * @return the multiplied vector
     */
    public static Point Multiply(Point A, float multiplier) {
        return new Point(A.getX() * multiplier, A.getY() * multiplier, A.getZ() * multiplier);
    }

    /**
     * Divides a vectorss components by the specified amount
     * @param A the vector to divide
     * @param divisor the amount to divide by
     * @return the divided vector
     */
    public static Point Divide(Point A, float divisor) {
        return new Point(A.getX() / divisor, A.getY() / divisor, A.getZ() / divisor);
    }

    /**
     * inverts the components of the vector
     * @param A the vector to invert
     * @return the inverted vector
     */
    public static Point invertPoint(Point A) {
        return Multiply(A, -1);
    }

    /**
     * Pythagorean to get magnitude of the vector
     * @return the length of the vector
     */
    public static float Length(Point p) {
        return (float) Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2) + Math.pow(p.getZ(), 2));
    }

    /**
     * get the squared magnitude of the vector
     * @return the un-rooted length of the vector (root is expensive)
     */
    public static float Length2(Point p) {
        return (float) (Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2) + Math.pow(p.getZ(), 2));
    }

    /**
     * Get the normalized vector (magnitude of 1)
     * @return the same vector, but it has a magnitude of 1
     */
    public static Point Normalize(Point p) {
        return Divide(p, Length(p));
    }

    /**
     * Get the dot product of two vectors
     * @param A vector 1
     * @param B vector 2
     * @return the dot product of the vectors
     */
    public static float Dot(Point A, Point B) {
        float out = 0;
        float[] Acomps = A.getPos();
        float[] Bcomps = B.getPos();
        for (int i = 0; i < 3; i++)
            out += Acomps[i] * Bcomps[i];

        return out;
    }

    /**
     * Reflects a point around another point
     * @param ray the original point
     * @param reflect the point to reflect around
     * @return the reflected point
     */
    public static Point ReflectAround(Point ray, Point reflect) {
        float dot = Dot(ray, reflect);
        Point out = new Point();
        out.setX(-2*dot* reflect.getX());
        out.setY(-2*dot* reflect.getY());
        out.setZ(-2*dot* reflect.getZ());
        out = Subtract(ray, out);
        return out;
    }
}

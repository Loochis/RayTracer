package LoochisMath;

import shapes.Point;

public class PointMath {

    /**
     * Finds the average point of an array of points
     * @param points an array of points, unlimited length
     * @return the average of the position of all points
     */
    public static Point averagePoints(Point[] points) {
        float newX = 0;
        float newY = 0;
        float newZ = 0;
        for (Point point : points) { // Add up all the x and y components
            newX += point.getX();
            newY += point.getY();
            newZ += point.getZ();
        }
        newX /= points.length; // Divide by number of averaged points
        newY /= points.length;
        newZ /= points.length;
        return new Point(newX, newY, newZ); // Return average point
    }

    /**
     * Rotates a point about another point on the Z axis by a certain amount
     * @param r Point to be rotated
     * @param pivot Point to rotate around
     * @param d number in radians to rotate by (+)=CW, (-)=CCW
     */
    public static Point rotateAroundZ(Point r, Point pivot, float d) {
        double zDif = r.getZ() - pivot.getZ(); // Get z Difference
        double yDif = r.getY() - pivot.getY(); // Get y Difference
        r.setX((float) (zDif*Math.cos(d) - yDif*Math.sin(d))); // Rotate point in pivot-space
        r.setY((float) (zDif*Math.sin(d) + yDif*Math.cos(d))); // Rotate point in pivot-space
        r.setZ(pivot.getZ());
        return VectorMath.Add(r, pivot); // translate back to global-space
    }

    /**
     * Rotates a point about another point on the Y axis by a certain amount
     * @param r Point to be rotated
     * @param pivot Point to rotate around
     * @param d number in radians to rotate by (+)=CW, (-)=CCW
     */
    public static Point rotateAroundY(Point r, Point pivot, float d) {
        double xDif = r.getX() - pivot.getX(); // Get x Difference
        double zDif = r.getZ() - pivot.getZ(); // Get z Difference
        r.setX((float) (xDif*Math.cos(d) + zDif*Math.sin(d))); // Rotate point in pivot-space
        r.setZ((float) (-xDif*Math.sin(d) + zDif*Math.cos(d))); // Rotate point in pivot-space
        r.setY(pivot.getY());
        return VectorMath.Add(r, pivot); // translate back to global-space
    }

    /**
     * Rotates a point about another point on the X axis by a certain amount
     * @param r Point to be rotated
     * @param pivot Point to rotate around
     * @param d number in radians to rotate by (+)=CW, (-)=CCW
     */
    public static Point rotateAroundX(Point r, Point pivot, float d) {
        float zDif = r.getZ() - pivot.getZ(); // Get z Difference
        float yDif = r.getY() - pivot.getY(); // Get y Difference
        r.setZ((float) (yDif*Math.cos(d) - zDif*Math.sin(d))); // Rotate point in pivot-space
        r.setY((float) (yDif*Math.sin(d) + zDif*Math.cos(d))); // Rotate point in pivot-space
        return VectorMath.Add(r, pivot); // translate back to global-space
    }

    /**
     * Scales a point about another point by a certain amount
     * @param r Point to be scaled
     * @param pivot Point to scale around
     * @param d amount to scale by
     */
    public static Point scaleAround(Point r, Point pivot, float d) {
        float xDif = r.getX() - pivot.getX(); // Get x Difference (Move point to rotate about origin)
        float yDif = r.getY() - pivot.getY(); // Get y Difference (Move point to rotate about origin)
        float zDif = r.getZ() - pivot.getZ(); // Get y Difference (Move point to rotate about origin)
        r.setX(xDif * d); // Scale point in pivot-space
        r.setY(yDif * d); // Scale point in pivot-space
        r.setZ(zDif * d); // Scale point in pivot-space
        return VectorMath.Add(r, pivot); // translate back to global-space
    }
}

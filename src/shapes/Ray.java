package shapes;

import LoochisMath.PointMath;
import LoochisMath.VectorMath;

public class Ray {

    private Point origin, head; // Head of ray is relative to origin
    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Point getHead() {
        return head;
    }

    public void setHead(Point head) {
        this.head = head;
    }

    /**
     * Constructor for ray
     * @param origin origin of the ray
     * @param head direction the ray points in (not rotation)
     * @param relative if true, head is relative to origin
     */
    public Ray(Point origin, Point head, boolean relative) {
        this.origin = origin;
        if (relative)
            this.head = head;
        else
            this.head = VectorMath.Subtract(head, origin); // Move head to local-space
    }

    public Ray Normalized() {
        return new Ray(origin, VectorMath.Normalize(head), true);
    }
}

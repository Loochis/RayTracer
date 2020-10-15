package shapes;

public class Point {

    public float x, y, z; // Individual components of the point
    public float[] pos;   // Array of components of the point

    /**
     * Default point constructor
     */
    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        pos = new float[] {0, 0, 0};
    }

    /**
     * Overloaded point constructor
     * @param x X position of the point
     * @param y Y position of the point
     * @param z Z position of the point
     */
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        pos = new float[] {x, y, z};
    }
}

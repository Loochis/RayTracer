package shapes;

public class Point {

    private float x, y, z; // Individual components of the point
    private float[] pos;   // Array of components of the point

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

    /**
     * Array point constructor
     * @param comps the components as an array of floats
     */
    public Point(float[] comps) {
        this.x = comps[0];
        this.y = comps[1];
        this.z = comps[2];
        pos = comps;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        updatePos();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        updatePos();
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
        updatePos();
    }

    public float[] getPos() {
        return pos;
    }

    public void setPos(float[] pos) {
        this.pos = pos;
        x = pos[0]; // Update components
        y = pos[1];
        z = pos[2];
    }

    private void updatePos() { // Update array from new components
        pos = new float[] {x, y, z};
    }
}

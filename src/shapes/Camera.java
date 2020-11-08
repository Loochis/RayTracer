package shapes;

import LoochisMath.PointMath;
import rayTracing.Intersection;
import rayTracing.Main;

import java.awt.*;

public class Camera extends Shape{

    private int vres;
    private boolean ortho = true;

    int xPix, yPix;

    public int getVres() {
        return vres;
    }

    public void setVres(int vres) {
        this.vres = vres;
    }

    public boolean isOrtho() {
        return ortho;
    }

    public void setOrtho(boolean ortho) {
        this.ortho = ortho;
    }

    private Point[] originPoints;
    private Point[] rayPoints;

    public Camera(Point pos, Point rot, int zoom, int vres, boolean ortho) {
        super(pos, rot, zoom, null);
        this.vres = vres;
        this.ortho = ortho;
    }

    private void recalculatePoints() {
        xPix = Main.WIDTH / vres; // Get number of vpixels on each dimension
        yPix = Main.HEIGHT / vres;
        originPoints = new Point[xPix * yPix];
        rayPoints = new Point[xPix * yPix];

        for (int i = 0; i < originPoints.length; i++) {
            originPoints[i] = IDToCoord(i);
        }
    }

    public Point IDToCoord(int i) {
        Point out = new Point();
        out.setX(i % xPix - (int)(xPix / 2) + super.getPos().getX()); // find worldspace X
        out.setY(Math.floorDiv(i, yPix) - (int)(yPix / 2) + super.getPos().getY()); // Find worldspace Y
        out.setZ(super.getPos().getZ()); // Find worldspace Z
        PointMath.scaleAround(out, super.getPos(), (float) super.getScale()); // Scale point
        PointMath.rotateAroundY(out, super.getPos(), super.getRot().getY());  // Rotate point on Y
        return out;
    }

    @Override
    public Point[] getPoints() {
        return originPoints;
    }

    @Override
    public Intersection collisionTest(Ray ray) {
        return null;
    }
}

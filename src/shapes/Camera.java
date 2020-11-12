package shapes;

import LoochisMath.PointMath;
import LoochisMath.VectorMath;
import rayTracing.Intersection;
import rayTracing.Main;

import java.awt.*;
import java.util.Random;

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
        recalculatePoints();
    }

    public void recalculatePoints() {
        xPix = Main.WIDTH; // Get number of vpixels on each dimension
        yPix = Main.HEIGHT;
        originPoints = new Point[xPix * yPix];
        rayPoints = new Point[xPix * yPix];

        for (int i = 0; i < originPoints.length; i++) {
            originPoints[i] = IDToCoord(i, new Random());
        }
    }

    public Point IDToCoord(int i, Random rand) {
        Point out = new Point();
        out.setX(i % xPix - (int)(xPix / 2) + super.getPos().getX() + rand.nextFloat() * vres); // find worldspace X
        out.setY(Math.floorDiv(i, xPix) - (int)(yPix / 2) + super.getPos().getY() + rand.nextFloat() * vres); // Find worldspace Y
        out.setZ(super.getPos().getZ()); // Find worldspace Z
        out = PointMath.scaleAround(out, super.getPos(), super.getScale()); // Scale point
        out = PointMath.rotateAroundX(out, super.getPos(), super.getRot().getX());  // Rotate point on Y
        return out;
    }

    public Point IDToRay(int i, Random rand) {
        Point out = new Point();
        out.setX(i % xPix - (int)(xPix / 2) + super.getPos().getX() + rand.nextFloat() * vres); // find worldspace X
        out.setY(Math.floorDiv(i, xPix) - (int)(yPix / 2) + super.getPos().getY() + rand.nextFloat() * vres); // Find worldspace Y
        out.setZ(super.getPos().getZ() + 1); // Find worldspace Z (add 1 to shoot in a direction)
        out = PointMath.scaleAround(out, super.getPos(), super.getScale()); // Scale point
        out = PointMath.rotateAroundX(out, super.getPos(), super.getRot().getX());  // Rotate point on Y
        return out;
    }

    public int CoordsToID(int x, int y) {
        return (x + (y * xPix));
    }

    public Point ScreenToCameraSpace(Point p) {
        Point out = new Point();
        out.setX(p.getX() - (int)(xPix / 2));
        out.setY(-p.getY() + (int)(yPix / 2));
        out = PointMath.scaleAround(out, super.getPos(), super.getScale());
        out.setX(out.getX() + super.getPos().getX());
        out.setY(out.getY() + super.getPos().getY());
        out.setZ(p.getZ());
        return out;
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
        super.updateRotation(VectorMath.Add(super.getRot(), rot));
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

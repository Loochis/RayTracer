package shapes;

import LoochisMath.VectorMath;
import rayTracing.Intersection;

import java.awt.*;
import java.util.Random;

public class Lamp extends Sphere {

    private float intensity;

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * get the jittered position of the lamp
     * @param jittered whether or not to jitter the lamps position
     * @return the position of the lamp
     */
    public Point getPos(boolean jittered) {
        if (super.getScale() == 0 || !jittered)
            return super.getPos();

        Random rand = new Random();
        Point out = new Point();
        out.setX(super.getPos().getX() + super.getScale() * (rand.nextFloat() * 2 - 1));
        out.setY(super.getPos().getY() + super.getScale() * (rand.nextFloat() * 2 - 1));
        out.setZ(super.getPos().getZ() + super.getScale() * (rand.nextFloat() * 2 - 1));
        return out;
    }

    public Lamp(Point pos, int radius, float intensity, Color color) {
        super(pos, radius, color);
        this.intensity = intensity;
    }
}

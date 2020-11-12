package shapes;

import LoochisMath.VectorMath;
import rayTracing.Intersection;

import java.awt.*;

public class Lamp extends Sphere {

    private float intensity;

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Lamp(Point pos, int radius, float intensity, Color color) {
        super(pos, radius, color);
        this.intensity = intensity;
    }
}

package rayTracing;

import LoochisMath.VectorMath;
import shapes.*;
import shapes.Point;
import shapes.Shape;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Render {
    private static final Color BACKGROUND_COLOR = new Color(29, 29, 29);
    private float currentBright = 1;
    private float maxBrightness = 1;
    private float cacheMaxBright = 1;

    /**
     * generates the image to draw to the screen
     * @param g passed in graphics
     * @param shapeList the list of shapes in the scene
     */
    public void generateImage(Graphics g, List<Shape> shapeList) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
        for (int x = 0; x < Main.WIDTH; x+= Main.VRES) {      // Cycle through pixels, skipping by virtual resolution
            for (int y = 0; y < Main.HEIGHT; y+= Main.VRES) {
                g.setColor(BACKGROUND_COLOR);
                Ray ray = new Ray(new Point(x, y, 0), new Point(0, 0, 1), true);
                Intersection initInter = intersectTest(ray, shapeList);
                if (initInter != null) {
                    if (Main.Z_IMAGE) {
                        float normalizedDepth = 1 - (initInter.getzDepth() / Main.MAX_DISTANCE);
                        g.setColor(new Color(normalizedDepth, normalizedDepth, normalizedDepth));
                    } else {
                        float bright = brightnessCalc(initInter, shapeList);
                        g.setColor(new Color((int) (initInter.getColor().getRed() * bright), (int) (initInter.getColor().getGreen() * bright), (int) (initInter.getColor().getBlue() * bright)));
                    }
                }
                g.fillRect(x, y, Main.VRES, Main.VRES);                                                                        // Draw a pixel
            }
        }
        maxBrightness = cacheMaxBright;
        cacheMaxBright = 1;

        if (Main.TIMED_BRIGHTNESS) {
            if (currentBright < maxBrightness - 0.1)
                currentBright *= 1.05;
            if (currentBright > maxBrightness)
                currentBright /= 1.05;
        } else
            currentBright = maxBrightness;

        System.out.println(currentBright);
    }

    /**
     * Performs an intersection test on all the shapes in the scene
     * @param ray the ray currently being tested
     * @param shapeList list of shapes in the scene
     * @return the intersection points and z-depths
     */
    private Intersection intersectTest(Ray ray, List<Shape> shapeList) {
        if (shapeList == null)
            return null;

        float zBuffer = Main.MAX_DISTANCE;
        Intersection intersection = null;

        for (Shape shape : shapeList) {
            Intersection newInter = shape.collisionTest(ray);

            if (newInter == null)
                continue;
            if (newInter.getzDepth() > zBuffer || newInter.getzDepth() < 0)
                continue;
            zBuffer = newInter.getzDepth();
            intersection = newInter;
        }
        return intersection;
    }

    private float brightnessCalc(Intersection i, List<Shape> shapeList) {

        float brightness = 0;

        if (i.getShape() instanceof Lamp)
            return 1;

        for (Object shape : shapeList) {
            if (shape instanceof Lamp) {
                Ray ray = new Ray(i.getPos1(), ((Lamp) shape).getPos(), false); // Create a ray from point to lamp
                ray = ray.Normalized();
                Intersection intersect = intersectTest(ray, shapeList);
                if (intersect != null && intersect.getShape() != shape) // If the ray of light is blocked, cycle
                    continue;
                float dot = VectorMath.Dot(ray.getHead(), i.getNormal()); // Get dot between normal of intersection and lamp ray
                if (dot > 0) {
                    brightness += ((Lamp) shape).getIntensity() * dot / (VectorMath.Length2(VectorMath.Subtract(((Lamp) shape).getPos(), i.getPos1()))); // inverse square of distance between lamp and intersection
                }
            }
        }

        if (brightness > cacheMaxBright)
            cacheMaxBright = brightness;

        if (brightness > currentBright) {
            brightness = currentBright;
        }

        brightness /= currentBright;
        brightness = (float)Math.sqrt(brightness);
        return brightness;
    }
}
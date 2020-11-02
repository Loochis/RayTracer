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
                    float bright = brightnessCalc(initInter, shapeList);
                    g.setColor(new Color((int) (bright * 200), (int) (bright * 200), (int) (bright * 200)));
                }
                g.fillRect(x, y, Main.VRES, Main.VRES);                                                                        // Draw a pixel
            }
        }
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

        float numLights = 0;
        float brightness = 0;

        for (Object shape : shapeList) {
            if (shape instanceof Lamp) {
                numLights++;
                Ray ray = new Ray(i.getPos1(), ((Lamp) shape).getPos(), false); // Create a ray from point to lamp
                ray = ray.Normalized();
                if (intersectTest(ray, shapeList) != null) // If the ray of light is blocked, cycle
                    continue;
                float dot = VectorMath.Dot(ray.getHead(), i.getNormal()); // Get dot between normal of intersection and lamp ray
                if (dot > 0) {
                    brightness += ((Lamp) shape).getScale() * dot / (VectorMath.Length2(VectorMath.Subtract(((Lamp) shape).getPos(), i.getPos1()))); // inverse square of distance between lamp and intersection
                }
            }
        }
        if (numLights == 0)
            return 1f;
        if (brightness > numLights)
            brightness = numLights;
        return (brightness / numLights);
    }
}

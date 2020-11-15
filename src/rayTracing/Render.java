package rayTracing;

import LoochisMath.ColorMath;
import LoochisMath.PointMath;
import LoochisMath.VectorMath;
import shapes.*;
import shapes.Point;
import shapes.Shape;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Render {
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0);
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
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        for (int x = 0; x < Main.WIDTH; x += Main.VRES) {      // Cycle through pixels, skipping by virtual resolution
            for (int y = 0; y < Main.HEIGHT; y += Main.VRES) {
                g.setColor(PixelCalc(x, y, shapeList));
                g.fillRect(x, y, Main.VRES, Main.VRES);
            }
        }
    }

    private Color PixelCalc(int x, int y, List<Shape> shapeList) {
        Color[] bufferColors = new Color[Main.SAMPLES];
        for (int s = 0; s < Main.SAMPLES; s++) {
            // Set up buffers for ray path calculation
            Ray bufferedRay = new Ray(new Point(x, y, 0), new Point(0, 0, 1), true);
            Intersection[] bufferedIntersections = new Intersection[Main.MAX_BOUNCES];
            for (int b = 0; b < Main.MAX_BOUNCES; b++) {
                Intersection i = Intersect(bufferedRay, shapeList);
                if (i == null)
                    break;
                bufferedIntersections[b] = i; // set this index of the buffered intersection to i
                Point reflectedHead = VectorMath.ReflectAround(bufferedRay.getHead(), i.getNormal());
                bufferedRay = new Ray(i.getPos1(), reflectedHead, true); // Reflect the ray and recurse
            }

            // Trace back through all intersections
            for (int b = Main.MAX_BOUNCES - 1; b > 0; b--) {
                if (bufferedIntersections[b] == null)
                    continue;
                Intersection i = bufferedIntersections[b];
                // Use the intersection position to get the amount of shadow of the point
                i.setColor(ColorMath.MultiplyColor(i.getColor(), brightnessCalc(i, shapeList)));

                // Average the colors moving backwards, if no color exists use plain color
                if (bufferColors[s] == null)
                    bufferColors[s] = i.getColor();
                else
                    bufferColors[s] = ColorMath.AverageColor(new Color[] {bufferColors[s], i.getColor()});
            }
            if (bufferColors[s] == null)
                bufferColors[s] = BACKGROUND_COLOR;
        }
        return ColorMath.AverageColor(bufferColors);
    }

    private Intersection Intersect(Ray ray, List<Shape> shapeList) {
        Intersection intersectBuffer = null;
        for (Shape shape : shapeList) {
            Intersection intersection = shape.collisionTest(ray);
            if (intersection == null) // If there is no intersecction, return null
                continue;
            if (intersectBuffer == null ||  intersection.getzDepth() < intersectBuffer.getzDepth()) { // Replace with closer intersection
                intersectBuffer = intersection;
            }
        }
        return intersectBuffer;
    }

    private float brightnessCalc (Intersection i, List<Shape> shapeList) {
        float brightness = 0;
        if (i.getShape() instanceof Lamp)
            return 10;
        for (Shape shape : shapeList) {
            if (!(shape instanceof Lamp))
                continue;
            Ray shadowRay = new Ray(i.getPos1(), shape.getPos(), false);
            shadowRay = shadowRay.Normalized();
            Intersection si = Intersect(shadowRay, shapeList);
            if (si.getShape() == shape) { // Check if there is a ray intersection with the current lamp
                Lamp l = (Lamp) si.getShape();
                float dot = VectorMath.Dot(si.getNormal(), shadowRay.getHead());

                // Use inverse square law to get the brightness of the pixel
                if (dot > 0)
                    brightness += Math.sqrt(l.getIntensity() / VectorMath.Length2(VectorMath.Subtract(l.getPos(), i.getPos1())));
            }
        }
        return brightness;
    }
}
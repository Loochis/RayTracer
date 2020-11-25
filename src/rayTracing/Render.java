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
    private static final Color BACKGROUND_COLOR = new Color(29, 29, 29);
    private static final Color AMBIENT_LIGHT = new Color(144, 144, 144);
    private static final float PHONG_CONSTANT = 31f;
    private float currentBright = 1;
    private float maxBrightness = 1;
    private float cacheMaxBright = 1;

    // Holds all the pixel colors in a cache to be drawn to screen during render
    private Color[][] colorCache = new Color[Main.WIDTH][Main.HEIGHT];

    public Color[][] getColorCache() {
        return colorCache;
    }

    /**
     * generates the image to draw to the screen during rendering process
     * @param g passed in graphics
     */
    public void generateImage(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        for (int x = 0; x < Main.WIDTH; x += Main.VRES) {      // Cycle through pixels, skipping by virtual resolution
            for (int y = 0; y < Main.HEIGHT; y += Main.VRES) {
                if (colorCache[x][y] != null) {
                    g.setColor(colorCache[x][y]);
                    g.fillRect(x, y, Main.VRES, Main.VRES);
                }
            }
        }
    }

    public void generatePixel(int x, int y, List<Shape> shapeList) {
        colorCache[x][y] = PixelCalc(x, y, shapeList);
    }

    private Color PixelCalc(int x, int y, List<Shape> shapeList) {
        Color[] bufferColors = new Color[Main.SAMPLES];
        for (int s = 0; s < Main.SAMPLES; s++) {
            Random rand = new Random();
            rand.setSeed(x + y + s);
            // Set up buffers for ray path calculation
            Ray bufferedRay = new Ray(new Point(x + Main.VRES*rand.nextFloat(), y + Main.VRES* rand.nextFloat(), 0), new Point(0, 0, 1), true);
            Intersection i = IntersectTest(bufferedRay, shapeList, null,true);
            if (i == null) {
                bufferColors[s] = BACKGROUND_COLOR;
                continue;
            }
            bufferColors[s] = PhongGlossBlend(bufferedRay, i, shapeList, 0);
        }
        return ColorMath.AverageColor(bufferColors);
    }

    private Intersection IntersectTest(Ray ray, List<Shape> shapeList, Shape sender, boolean lights) {
        Intersection cachedInter = null;
        // Loop through all shapes in scene
        for (Shape shape : shapeList) {
            if (sender != null && sender == shape)
                continue;
            Intersection currentInter = shape.collisionTest(ray);
            if (currentInter == null)
                continue;
            // Big statement to check if intersection is valid
            if (!(cachedInter == null || (currentInter.getzDepth() < cachedInter.getzDepth())))
                continue;
            if (currentInter.getzDepth() <= 0)
                continue;
            if (!lights && currentInter.getShape() instanceof Lamp)
                continue;
            cachedInter = currentInter;
        }
        return cachedInter;
    }

    // Uses Phong lighting model
    private Color PhongCalc(Ray inRay, Ray outRay, Intersection i, List<Shape> shapeList) {
        if (i.getShape() instanceof Lamp)
            return i.getShape().getColor();

        Color[] result = new Color[Main.MAX_LIGHTS];
        Color ambient = ColorMath.MultiplyColor(i.getColor(), AMBIENT_LIGHT);
        int currentIndex = 0;
        for (Shape shape : shapeList) {
            if (!(shape instanceof Lamp))
                continue;

            Lamp l = (Lamp) shape;
            Point jitteredLampPos = l.getPos(true);
            Ray lampRay = new Ray(i.getPos1(), jitteredLampPos, false);
            lampRay = lampRay.Normalized();

            float brightness = 0f;
            Intersection lampInterTest = IntersectTest(lampRay, shapeList, i.getShape(), false);
            if (lampInterTest == null) {
                float distanceToLamp2 = VectorMath.Length2(VectorMath.Subtract(lampRay.getOrigin(), jitteredLampPos));
                brightness = l.getIntensity() / distanceToLamp2;
            }
            // Find the diffuse color by multiplying combined colors by the dot product of the normal and the lamps direction
            Color out = ColorMath.MultiplyColor(ColorMath.MultiplyColor(i.getColor(), l.getColor()), VectorMath.Dot(i.getNormal(), lampRay.getHead()));
            out = ColorMath.MultiplyColor(out, brightness);

            float highlightVal = (float) Math.pow(VectorMath.Dot(lampRay.getHead(), outRay.getHead()), PHONG_CONSTANT);
            Color highlight = ColorMath.MultiplyColor(ColorMath.MultiplyColor(l.getColor(), i.getColor()), highlightVal);
            highlight = ColorMath.MultiplyColor(highlight, brightness);

            out = ColorMath.AddColor(out, highlight);
            result[currentIndex] = out;
            currentIndex++;
        }
        Color finalOut = ColorMath.AddColor(result);
        return ColorMath.AddColor(finalOut, ambient);
    }

    private Color GlossyCalc(Ray inRay, Ray outRay, Intersection i, List<Shape> shapeList, int bounceIndex) {
        if (i.getShape() instanceof Lamp)
            return i.getShape().getColor();

        // Stop reflecting at max bounces
        if (bounceIndex > Main.MAX_BOUNCES)
            return i.getShape().getColor();

        Color out = i.getColor();

        i = IntersectTest(outRay, shapeList, i.getShape(), true);
        if (i == null)
            return BACKGROUND_COLOR;

        out = ColorMath.AverageColor(new Color[] {out, PhongGlossBlend(outRay, i, shapeList, bounceIndex + 1)});

        return out;
    }

    private Color PhongGlossBlend(Ray bufferedRay, Intersection i, List<Shape> shapeList, int bounceIndex) {

        Point reflectedHead = VectorMath.ReflectAround(bufferedRay.getHead(), i.getNormal());
        Ray reflectedRay = new Ray(i.getPos1(), reflectedHead, true);
        reflectedRay = reflectedRay.Normalized();

        Color phong = null;
        Color glossy = null;
        if (i.getShape().getGlossy() != 1) {
            // Multiply color by inverse of glossiness (diffuse)
            phong = ColorMath.MultiplyColor(PhongCalc(bufferedRay, reflectedRay, i, shapeList), (float) (1 - i.getShape().getGlossy()));
        }
        if (i.getShape().getGlossy() != 0) {

            // Multiply color by inverse of glossiness (diffuse)
            glossy = ColorMath.MultiplyColor(GlossyCalc(bufferedRay, reflectedRay, i, shapeList, bounceIndex), (float) (i.getShape().getGlossy()));
        }
        Color out = null;
        if (phong == null)
            out = glossy;
        else if (glossy == null)
            out = phong;
        else
            out = ColorMath.AddColor(phong, glossy);
        return out;
    }
}
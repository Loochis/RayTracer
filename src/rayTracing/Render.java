package rayTracing;

import shapes.Point;
import shapes.Ray;
import shapes.Shape;
import shapes.Sphere;

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
                Ray ray = new Ray(new Point(x, y, 0), new Point(0, 0, 1), true);
                g.setColor(intersectTest(ray, shapeList));
                g.fillRect(x, y, Main.VRES, Main.VRES);                                                                        // Draw a pixel
            }
        }
    }

    /**
     * Performs an intersection test on all the shapes in the scene
     * @param ray the ray currently being tested
     * @param shapeList list of shapes in the scene
     * @return the color of the intersected shape
     */
    private Color intersectTest(Ray ray, List<Shape> shapeList) {
        if (shapeList == null)
            return BACKGROUND_COLOR;
        for (Object shape : shapeList) {
            if (shape instanceof Sphere)
                if (((Sphere) shape).collisionTest(ray) != null)
                    return ((Sphere) shape).getColor();
        }
        return BACKGROUND_COLOR;
    }
}

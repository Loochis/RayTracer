package rayTracing;

import shapes.Lamp;
import shapes.Point;
import shapes.Shape;
import shapes.Sphere;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Main extends Canvas implements Runnable {

    public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
    public static final int WIDTH = SCREENSIZE.width, HEIGHT = SCREENSIZE.height;           // Width / height component of screen dimensions
    public static final int VRES = 1;                                             // Virtual resolution of the image

    public static final float MAX_DISTANCE = 10000; // maximum distance from camera

    private Render render = new Render();

    private boolean running = false;

    private List<Shape> shapeList = new ArrayList<>();

    public Main() {
        Window window = new Window(WIDTH, HEIGHT, "Raytracer V0", this);
    } // Create window on instantiation

    public static void main(String[] args) {
        new Main();
    } // Create instance of main to run in window

    public void start() {
        Thread thread = new Thread(this); // Create new thread
        running = true;
        shapeList.add(new Sphere(new Point(800, 500, 500), 200, Color.BLUE));
        shapeList.add(new Sphere(new Point(600, 500, 400), 100, Color.RED));
        shapeList.add(new Lamp(new Point(300, 500, 100), 100000));
        shapeList.add(new Lamp(new Point(1000, 1000, 100), 100000));
        thread.start(); // Run thread
    }

    /**
     * This executes inside of a thread, multithreading to be added
     */
    @Override
    public void run() {
        while(running) {
            SceneRender();
        }
    }

    /**
     * Renders the scene, including all objects
     */
    private void SceneRender() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs==null) {
            this.createBufferStrategy(3);  // If buffer strategy does not exist create a new one
            bs = this.getBufferStrategy();
        }
        Graphics g = bs.getDrawGraphics(); // Get graphics of buffer strategy
        render.generateImage(g, shapeList);           // Pass graphics into Render for them to get drawn
        g.dispose();
        bs.show(); // Draw the graphics
    }
}

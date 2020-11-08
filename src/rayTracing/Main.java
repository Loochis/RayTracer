package rayTracing;

import Listeners.MouseMoveListen;
import shapes.Lamp;
import shapes.Point;
import shapes.Shape;
import shapes.Sphere;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Canvas implements Runnable {

    public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
    public static final int WIDTH = SCREENSIZE.width, HEIGHT = SCREENSIZE.height;           // Width / height component of screen dimensions

    public static final boolean Z_IMAGE = false;
    public static final boolean ADAPTIVE_EXPOSURE = true;
    public static final boolean TIMED_BRIGHTNESS = false;

    public static final int VRES = 3;                                             // Virtual resolution of the image
    public static final int NUM_THREADS = 8; // MUST BE EVEN

    public static final int THREAD_X = WIDTH / (NUM_THREADS / 2);
    public static final int THREAD_Y = HEIGHT / (2);

    public static final float MAX_DISTANCE = 700; // maximum distance from camera

    private Render render = new Render();

    private boolean running = false;

    private List<Shape> shapeList = new ArrayList<>();
    private Window window;
    private Shape movableShape = null;
    private MouseMoveListen mouseListener;

    private Thread[] threads = new Thread[NUM_THREADS];

    public Main() {
        window = new Window(WIDTH, HEIGHT, "Raytracer V0", this);
        start();
    } // Create window on instantiation

    public static void main(String[] args) {
        new Main();
    } // Create instance of main to run in window

    public void start() {
        threads[0] = new Thread(this); // Create new thread
        running = true;
        shapeList.add(new Sphere(new Point(800, 500, 500), 200, new Color(255, 0, 0)));
        shapeList.add(new Sphere(new Point(600, 500, 400), 100, new Color(0, 123, 255)));
        shapeList.add(new Sphere(new Point(1000, 300, 300), 150, new Color(255, 255, 255)));
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            shapeList.add(new Lamp(new Point(rand.nextInt(2000), rand.nextInt(1500), rand.nextInt(500)), 100000));
        }
        movableShape = new Lamp(new Point(1100, 700, 100), 100000);
        shapeList.add(movableShape);
        addMouseMotionListener(new MouseMoveListen(this));
        threads[0].start(); // Run thread
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

    public void OnMouseMove(int x, int y) {
        movableShape.setPos(new Point(x, y, 100));
    }
}

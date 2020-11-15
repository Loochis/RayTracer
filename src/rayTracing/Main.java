package rayTracing;

import Listeners.MouseMoveListen;
import shapes.*;
import shapes.Point;
import shapes.Shape;

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

    public static final int VRES = 10;        // Virtual resolution of the image
    public static final int SAMPLES = 10;     // Number of rays per pixel
    public static final int MAX_BOUNCES = 2; // Number of bounces per ray
    public static final int NUM_THREADS = 8;  // MUST BE EVEN

    public static final int THREAD_X = WIDTH / (NUM_THREADS / 2);
    public static final int THREAD_Y = HEIGHT / (2);

    public static final float MAX_DISTANCE = 10000; // maximum distance from camera

    private Render render = new Render();

    private boolean running = false;

    private List<Shape> shapeList = new ArrayList<>();
    private Window window;
    private Shape movableShape = null;
    private MouseMoveListen mouseListener;

    private Thread[] threads = new Thread[NUM_THREADS];

    public Camera mainCam;

    private float angle = 0;

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
        Sphere testSphere = new Sphere(new Point(1000, 500, 500), 200, new Color(255, 0, 0));
        testSphere.setRoughness(0f);
        shapeList.add(testSphere);

        Sphere sphere2 = new Sphere(new Point(1500, 800, 2000), 900, new Color(0, 122, 254));
        sphere2.setRoughness(0f);
        shapeList.add(sphere2);
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            shapeList.add(new Lamp(new Point(rand.nextInt(2000), rand.nextInt(1500), rand.nextInt(500)), 100, 1000000000, Color.WHITE));
        }
        movableShape = new Lamp(new Point(-300, 400, 300), 100,1000000000, Color.WHITE);
        shapeList.add(movableShape);
        addMouseMotionListener(new MouseMoveListen(this));

        mainCam = new Camera(new Point(), new Point(1.5f*(float)Math.PI,0,0), 1, VRES, true);
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
        if (mainCam != null)
            movableShape.setPos(new Point(x, y, 400));
    }
}

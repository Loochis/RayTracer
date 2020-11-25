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
    public static final int MAX_LIGHTS = 20;

    public static final int VRES = 2;        // Virtual resolution of the image
    public static final int SAMPLES = 10;     // Number of rays per pixel
    public static final int MAX_BOUNCES = 3; // Number of bounces per ray
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
        testSphere.setGlossy(1);
        shapeList.add(testSphere);

        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            //shapeList.add(new Lamp(new Point(rand.nextInt(2000), rand.nextInt(1500), rand.nextInt(500)), 10, 100000, Color.WHITE));
        }
        for (int i = 0; i < 50; i++) {
            Sphere temp = new Sphere(new Point(rand.nextInt(2000), rand.nextInt(1500), rand.nextInt(600) + (50*i)), rand.nextInt(300) + 50, new Color(255, 226, 0));
            temp.setGlossy(0);
            for (int ii = 0; ii < 100; ii++) {
                for (Shape sphere : shapeList) {
                    if (!(sphere instanceof Sphere))
                        continue;
                    if (temp.IsIntersecting((Sphere) sphere)) {
                        temp = new Sphere(new Point(rand.nextInt(2000), rand.nextInt(1500), rand.nextInt(600) + (50 * i)), rand.nextInt(300) + 50, new Color(251, 255, 100));
                        temp.setGlossy(0);
                    }
                }
            }
            shapeList.add(temp);
        }
        movableShape = new Lamp(new Point(1700, 300, 300), 200,1000000, new Color(255, 255, 255));
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
        //while(running) {
            SceneRender();
        //}
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

        for (int x = 0; x < Main.WIDTH; x += Main.VRES) {      // Cycle through pixels, skipping by virtual resolution

            for (int y = 0; y < Main.HEIGHT; y += Main.VRES) {

                render.generatePixel(x, y, shapeList);
            }
            if (x % 10 == 0) {
                Graphics g = bs.getDrawGraphics(); // Get graphics of buffer strategy
                render.generateImage(g);
                g.dispose();
                bs.show(); // Draw the graphics
            }
        }


        // Pass graphics into Render for them to get drawn
    }

    public void OnMouseMove(int x, int y) {
        //if (mainCam != null)
            //movableShape.setPos(new Point(x, y, 300));
    }
}

package rayTracing;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
    public static final int WIDTH = SCREENSIZE.width, HEIGHT = SCREENSIZE.height;           // Width / height component of screen dimensions
    public static final int VRES = 5;                                             // Virtual resolution of the image

    private Render render = new Render();

    private boolean running = false;

    public Main() {
        Window window = new Window(WIDTH, HEIGHT, "Raytracer V0", this);
    } // Create window on instantiation

    public static void main(String[] args) {
        new Main();
    } // Create instance of main to run in window

    public void start() {
        Thread thread = new Thread(this); // Create new thread
        running = true;
        thread.start();                          // Run thread
    }

    /**
     * This executes inside of a thread, multithreading to be added
     */
    @Override
    public void run() {
        while(running) {
            SceneRender();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        render.generateImage(g);           // Pass graphics into Render for them to get drawn
        g.dispose();
        bs.show(); // Draw the graphics
    }
}

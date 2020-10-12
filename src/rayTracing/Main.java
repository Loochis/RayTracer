package rayTracing;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen dimensions
    public static final int WIDTH = SCREENSIZE.width, HEIGHT = SCREENSIZE.height;

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

    @Override
    public void run() {
        while(running) {
            SceneRender();
        }
    }

    private void SceneRender() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs==null) {
            this.createBufferStrategy(3);
            bs = this.getBufferStrategy();
        }
        Graphics g = bs.getDrawGraphics();
        render.generateImage(g);
        g.dispose();
        bs.show();
    }
}

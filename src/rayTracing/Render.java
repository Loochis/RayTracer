package rayTracing;

import java.awt.*;
import java.util.Random;

public class Render {
    private static final Color BACKGROUND_COLOR = new Color(29, 29, 29);

    Random rand = new Random();
    public void generateImage(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
        for (int x = 0; x < Main.WIDTH; x+= Main.VRES) {      // Cycle through pixels, skipping by virtual resolution
            for (int y = 0; y < Main.HEIGHT; y+= Main.VRES) {
                g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))); // Create a random color
                g.fillRect(x, y, Main.VRES, Main.VRES);                                                                        // Draw a pixel
            }
        }
    }
}

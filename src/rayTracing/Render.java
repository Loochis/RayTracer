package rayTracing;

import java.awt.*;

public class Render {
    private static final Color BACKGROUND_COLOR = new Color(29, 29, 29);

    public void generateImage(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);
        g.setColor(Color.blue);
        g.fillRect(0,0,200,200);
    }
}

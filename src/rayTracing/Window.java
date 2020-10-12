package rayTracing;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {
    JFrame frame;
    public Window(int width, int height, String title, Main main) {
        frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(main);
        frame.pack();
        frame.setVisible(true);
        main.start();
    }
}

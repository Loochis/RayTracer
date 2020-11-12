package LoochisMath;

import java.awt.*;

public class ColorMath {
    public static Color AverageColor(Color[] colors) {
        float r = 0, g = 0, b = 0;
        for (Color color : colors) {
            r += Math.pow((float) color.getRed() / 255.0, 2);  // Use power to get proper color values
            g += Math.pow((float) color.getGreen() / 255.0, 2);// Damn you humans and your logarithmic tendencies
            b += Math.pow((float) color.getBlue() / 255.0, 2);
        }
        r /= colors.length;
        g /= colors.length;
        b /= colors.length;
        return new Color(r, g, b);
    }

    public static Color MultiplyColor(Color color, float a) {
        if (a >= 1)
            a = 1;
        if (a <= 0)
            a = 0;

        return new Color((int) (color.getRed() * a), (int) (color.getGreen() * a), (int) (color.getBlue() * a));
    }
}

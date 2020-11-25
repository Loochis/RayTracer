package LoochisMath;

import java.awt.*;

public class ColorMath {
    public static Color AverageColor(Color[] colors) {
        float r = 0, g = 0, b = 0;
        int length = colors.length;
        for (Color color : colors) {
            if (color == null) {
                length--;
                continue;
            }

            r += Math.pow((float) color.getRed() / 255.0, 2);  // Use power to get proper color values
            g += Math.pow((float) color.getGreen() / 255.0, 2);// Damn you humans and your logarithmic tendencies
            b += Math.pow((float) color.getBlue() / 255.0, 2);
        }
        r /= length;
        g /= length;
        b /= length;
        return new Color(r, g, b);
    }

    public static Color MultiplyColor(Color color, float a) {
        if (a >= 1)
            a = 1;
        if (a <= 0)
            a = 0;

        return new Color((int) (color.getRed() * a), (int) (color.getGreen() * a), (int) (color.getBlue() * a));
    }

    public static Color MultiplyColor(Color color, Color color2) {
        float r1 = (float) color.getRed() / 255.0f;
        float r2 = (float) color2.getRed() / 255.0f;
        float g1 = (float) color.getGreen() / 255.0f;
        float g2 = (float) color2.getGreen() / 255.0f;
        float b1 = (float) color.getBlue() / 255.0f;
        float b2 = (float) color2.getBlue() / 255.0f;

        return new Color(r1*r2, g1*g2, b1*b2);
    }

    public static Color MultiplyColor(Color[] colors) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == null)
                continue;
            r *= (float) colors[i].getRed() / 255.0f;
            g *= (float) colors[i].getGreen() / 255.0f;
            b *= (float) colors[i].getBlue() / 255.0f;
        }
        if (r > 1)
            r = 1;
        if (g > 1)
            g = 1;
        if (b > 1)
            b = 1;
        return new Color(r, g, b);
    }

    public static Color AddColor(Color color, Color color2) {
        float r1 = (float) color.getRed() / 255.0f;
        float r2 = (float) color2.getRed() / 255.0f;
        float g1 = (float) color.getGreen() / 255.0f;
        float g2 = (float) color2.getGreen() / 255.0f;
        float b1 = (float) color.getBlue() / 255.0f;
        float b2 = (float) color2.getBlue() / 255.0f;
        float r = r1+r2;
        if (r > 1)
            r = 1;
        float g = g1+g2;
        if (g > 1)
            g = 1;
        float b = b1+b2;
        if (b > 1)
            b = 1;
        return new Color(r, g, b);
    }

    public static Color AddColor(Color[] colors) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == null)
                continue;
            r += (float) colors[i].getRed() / 255.0f;
            g += (float) colors[i].getGreen() / 255.0f;
            b += (float) colors[i].getBlue() / 255.0f;
        }
        if (r > 1)
            r = 1;
        if (g > 1)
            g = 1;
        if (b > 1)
            b = 1;
        return new Color(r, g, b);
    }
}

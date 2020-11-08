package Listeners;

import rayTracing.Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveListen implements MouseMotionListener {

    private Main main;
    public MouseMoveListen(Main main) {
        this.main = main;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        main.OnMouseMove(e.getX(), e.getY());
    }
}

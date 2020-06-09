package com.company.view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ZoomListener implements MouseWheelListener {

    public view.GraphicComponent graphic;
    public InputPanel buttons;
    public MainFrame mainFrame;

    public ZoomListener(MainFrame mainFrame, view.GraphicComponent graphic, InputPanel buttons) {
        this.mainFrame = mainFrame;
        this.graphic = graphic;
        this.buttons = buttons;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        if (event.getPreciseWheelRotation() < 0 && KeyEvent.VK_CONTROL != 0) {
            graphic.incrementUnitSegment();
            graphic.repaint();
        }
        if (event.getPreciseWheelRotation() > 0 && KeyEvent.VK_CONTROL != 0) {
            graphic.decrementUnitSegment();
            graphic.repaint();
        }
    }
}
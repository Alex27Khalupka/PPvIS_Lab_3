package com.company.view;

import com.company.controller.Controller;
import com.company.model.LinearFunction;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainFrame {
    public JFrame frame;
    public Controller controller;
    private int width = 900;
    private int height = 700;
    private InputPanel inputPanel;
    private PointsTable mainPointsTable;
    private view.GraphicComponent graphic;
    public LinearFunction calc;
    public JScrollPane scroll;
    private ReentrantLock lock;

    public view.GraphicComponent getGraphic() {
        return graphic;
    }

    public PointsTable getMainPointsTable() {
        return mainPointsTable;
    }

    public MainFrame() {
        lock = new ReentrantLock();
        controller = new Controller(MainFrame.this, lock);
        frame = new JFrame();
        inputPanel = new InputPanel();
    }

    public JFrame buildFrame() {
        frame.setTitle("Function Drawing");
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new BorderLayout());
        graphic = new view.GraphicComponent(controller);
        mainPointsTable = new PointsTable(this);
        scroll = new JScrollPane(graphic);
        scroll.setPreferredSize(new Dimension(605, 505));
        scroll.setAutoscrolls(true);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        frame.add(mainPointsTable.buildComponent(), BorderLayout.WEST);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(inputPanel.buildComponent(), BorderLayout.EAST);
        view.HoldAndDragListener listener = new view.HoldAndDragListener(graphic);
        scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        scroll.getViewport().addMouseListener(listener);
        scroll.getViewport().addMouseMotionListener(listener);
        ZoomListener zoomListener = new ZoomListener(MainFrame.this, graphic, inputPanel);
        scroll.addMouseWheelListener(zoomListener);
        inputPanel.getMainButton().addActionListener(event -> {
            controller.stopThreads();
            mainPointsTable.clear();
            graphic.clear();
            startCalculation();
        });
        inputPanel.getButtonStop().addActionListener(event -> {
            controller.stopThreads();
            mainPointsTable.clear();
            graphic.clear();
        });
        return frame;
    }

    public void startCalculation() {
        controller.startLinearFunctionThread();
        controller.startSortFunctionThread(inputPanel.getN(), inputPanel.getK());
    }

}

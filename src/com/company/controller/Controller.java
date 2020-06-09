package com.company.controller;

import com.company.model.LinearFunction;
import com.company.model.SortFunction;
import com.company.view.MainFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Controller {
    public MainFrame window;
    private LinearFunction linearFunction;
    private SortFunction sortFunction;
    private Lock lock;
    private List<Thread> threads;

    public Controller(MainFrame window, Lock lock) {
        this.window = window;
        this.lock = lock;
        this.linearFunction = new LinearFunction(lock, window);
        this.sortFunction = new SortFunction(1, 2, lock, window);
        this.threads = new ArrayList<>();
    }


    public void startLinearFunctionThread() {
        this.linearFunction = new LinearFunction(lock, window);
        Thread LinearThread = new Thread(linearFunction);
        threads.add(LinearThread);
        LinearThread.start();
    }

    public void startSortFunctionThread(int n, int k) {
        this.sortFunction = new SortFunction(n, k, lock, window);
        Thread sortThread = new Thread(sortFunction);
        threads.add(sortThread);
        sortThread.start();
    }

    public void stopThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }
}

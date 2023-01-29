package com.caroli.cykel;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class SyncRequest extends Thread implements Runnable {

    private ArrayList<Item> items;
    private Thread syncThread;
    ExecutorService executor;

    SyncRequest(ArrayList<Item> items, ExecutorService executor) {
        this.items = items;
        this.executor = executor;
    }

    @Override
    public void run() {
        if (Thread.currentThread().isDaemon()) {
            System.out.println("daemon thread work");
        } else {
            System.out.println("user thread work");
        }
        try {
            syncThread = Thread.currentThread();
            ApiRequest request = new ApiRequest();
            request.syncReq(items);
            cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        executor.shutdown();
        executor.shutdownNow();
        syncThread.interrupt();
        //syncThread.stop();

        System.out.println("exit thread");
    }
}

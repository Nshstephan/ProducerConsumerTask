package com.vmware;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Producer> producersList = new ArrayList<>();

    public static void main(String[] args) {

        Buffer sharedLocation = new BlockingBuffer();

        // CTRL+C will trigger this thread.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {

                for (var runnable : producersList) {
                    runnable.setRunning(false);
                }

                System.out.println("Waiting for all consumers to save all queued data.");

                while (sharedLocation.getQueueSize() > 0) {
                    Thread.sleep(1000);
                }

                System.out.println("Done!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }));

        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        int producerCount = Integer.parseInt(args[0]);
        int consumerCount = Integer.parseInt(args[1]);

        if (producerCount < 1 || producerCount > 10 || consumerCount < 1 || consumerCount > 10) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < producerCount; ++i) {
            Producer pr = new Producer(sharedLocation, i + 1);
            producersList.add(pr);
            new Thread(pr).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("\nSize of queue: " + sharedLocation.getQueueSize());
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        for (int i = 0; i < consumerCount; ++i) {
            new Thread(new Consumer(sharedLocation, i + 1)).start();
        }
    }
}
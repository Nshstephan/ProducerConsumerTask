package com.vmware;

import java.security.SecureRandom;

public class Producer implements Runnable {

    private static final SecureRandom generator = new SecureRandom();
    private final Buffer sharedLocation;
    private int id;
    private volatile boolean isRunning = true;

    public Producer(Buffer sharedLocation, int id) {
        this.sharedLocation = sharedLocation;
        this.id = id;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {

        boolean needWait = false;
        int size, value;

        while (isRunning) {

            try {
                Thread.sleep(generator.nextInt(100));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }

            size = sharedLocation.getQueueSize();

            if (size >= 100) {
                needWait = true;
                continue;
            }

            if (needWait) {
                if (size > 80) {
                    continue;
                }
                needWait = false;
            }

            try {
                value = generator.nextInt(99) + 1;
                System.out.printf("%s%d%s%2d\t%s%d%n", "Producer ", id,
                        " writes ", value, "Buffer cells occupied: ", size);
                sharedLocation.blockingPut(value);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.printf("%nProducer " + id + " done producing. Terminating Producer.%n");
    }
}
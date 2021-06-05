package com.vmware;

import java.security.SecureRandom;

public class Consumer implements Runnable {

    private static final SecureRandom generator = new SecureRandom();
    private final Buffer sharedLocation;
    private int id;

    public Consumer(Buffer sharedLocation, int id) {
        this.sharedLocation = sharedLocation;
        this.id = id;
    }

    @Override
    public void run() {

        int consumedValue;

        while (true) {
            try {
                Thread.sleep(generator.nextInt(100));
                consumedValue = sharedLocation.blockingGet(id);
                Writer.writeToFile(consumedValue);
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
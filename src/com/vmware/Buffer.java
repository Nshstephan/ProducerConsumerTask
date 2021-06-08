package com.vmware;

public interface Buffer {

    void blockingPut(int value) throws InterruptedException;

    int blockingGet() throws InterruptedException;

    int getQueueSize();
}

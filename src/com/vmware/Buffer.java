package com.vmware;

public interface Buffer {

    void blockingPut(int value, int id) throws InterruptedException;

    int blockingGet(int id) throws InterruptedException;

    int getQueueSize();
}

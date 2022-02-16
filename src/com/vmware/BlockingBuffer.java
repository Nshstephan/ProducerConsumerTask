package com.vmware;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//Creating a synchronized buffer using an LinkedBlockingQueue.
public class BlockingBuffer implements Buffer {

    private final BlockingQueue<Integer> buffer;

    public BlockingBuffer() {
        this.buffer = new LinkedBlockingQueue<>();
    }

    public int getQueueSize() {
        return buffer.size();
    }

    // place value into buffer
    public void blockingPut(int value) throws InterruptedException {
        buffer.put(value);
    }

    // return value from buffer
    public int blockingGet() throws InterruptedException {
        return  buffer.take();
    }
}

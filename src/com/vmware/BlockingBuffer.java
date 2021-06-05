package com.vmware;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//Creating a synchronized buffer using an LinkedBlockingQueue.
public class BlockingBuffer implements Buffer {

    private final BlockingQueue<Integer> buffer;

    public BlockingBuffer() {
        this.buffer = new LinkedBlockingQueue<>();
    }

    public synchronized int getQueueSize() {
        return buffer.size();
    }

    // place value into buffer
    public void blockingPut(int value, int id) throws InterruptedException {
        buffer.put(value);
        System.out.printf("%s%d%s%2d\t%s%d%n", "Producer ", id,
                " writes ", value, "Buffer cells occupied: ", buffer.size());
    }

    // return value from buffer
    public int blockingGet(int id) throws InterruptedException {
        int readValue = buffer.take();
        System.out.printf("%s%d%s %2d\t%s%d%n", "Consumer ", id, " reads ", readValue,
                "Buffer cells occupied: ", buffer.size());

        return readValue;
    }
}
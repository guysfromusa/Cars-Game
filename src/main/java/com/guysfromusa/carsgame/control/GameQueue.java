package com.guysfromusa.carsgame.control;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
public class GameQueue {

    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    public void put(Message o) throws InterruptedException {
        queue.put(o);
    }

    public Message take() throws InterruptedException {
        return queue.take();
    }

    public void drainTo(Collection<? super Message> c) {
        queue.drainTo(c);
    }
}

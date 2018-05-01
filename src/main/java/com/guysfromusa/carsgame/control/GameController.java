package com.guysfromusa.carsgame.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
public class GameController {

    private final GameQueue gameQueue;

    @Autowired
    public GameController(GameQueue gameQueue) {
        this.gameQueue = notNull(gameQueue);
    }


    public CompletableFuture<String> handle(Message m) throws InterruptedException {
        gameQueue.put(m);
        return m.getFuture();
    }
}

package com.guysfromusa.carsgame.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class GameInterrupter {

    private final GameQueue gameQueue;

    @Autowired
    public GameInterrupter(GameQueue gameQueue) {
        this.gameQueue = gameQueue;
    }

    @Scheduled(fixedRate = 30_000)
    public void sendInterruptMessage() throws InterruptedException {
        log.info("Send message to interrupt all games");
        Message m = new Message();
        m.setGameName("Game - interrupt");
        gameQueue.put(m);
    }
}

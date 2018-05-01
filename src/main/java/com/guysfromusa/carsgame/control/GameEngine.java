package com.guysfromusa.carsgame.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sebastian Mikucki, 01.05.18
 */
@Component
@Slf4j
public class GameEngine {

    @Async
    public void handleMoves(List<Message> messages) {

        //TODO for all messages calculate movements and collisions

        messages.forEach(message -> {

            CompletableFuture<String> future = message.getFuture();
            try {
                TimeUnit.MILLISECONDS.sleep(15);
                //TODO think if complete before save to db
                log.info("Complete message");
                future.complete("{status:'OK'}");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        //TODO at the end save state into db

    }

    @Async
    public void handleInterrupt(List<Message> messages) {
        log.info("Interrupt game : " + messages);
        messages.forEach(message -> message.getFuture().complete("OK"));
    }


}

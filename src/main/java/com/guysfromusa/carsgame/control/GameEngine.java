package com.guysfromusa.carsgame.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Sebastian Mikucki, 01.05.18
 */
@Component
@Slf4j
public class GameEngine {

    //TODO add gameState here


    @Async
    public void handleMoves(List<Message> messages) {

        //TODO for all messages calculate movements and collisions

        //TODO temporary solution
        messages.forEach(message -> {
            CompletableFuture<String> future = message.getFuture();
            //TODO think if complete before save to db
            log.info("Complete message");
            future.complete("{status:'OK'}");
        });

        //TODO at the end save state into db

    }

}

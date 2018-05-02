package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.GameStateTracker;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 01.05.18
 */
@Component
@Slf4j
public class GameEngine {


    private final GameStateTracker gameStateTracker;
    private final CarController carController;

    @Inject
    public GameEngine(GameStateTracker gameStateTracker, CarController carController) {
        this.gameStateTracker = notNull(gameStateTracker);
        this.carController = notNull(carController);
    }

    @Async
    public void handleMoves(List<Message> messages) {

        messages.forEach(message -> {

            GameState gameState = gameStateTracker.getGameState(message.getGameName());
            String status = carController.calculateCarState((MovementMessage) message, gameState);

            CompletableFuture<String> future = message.getFuture();
            //TODO think if complete before save to db
            log.info("Complete message");
            future.complete(status);
        });

        //TODO at the end save state into db

    }

    public void createNewGame(List<Message> messages){
        messages.forEach(message -> {
            NewGameMessage newGameMessage = (NewGameMessage) message;
            String gameName = newGameMessage.getGameName();
            Integer[][] mapContent = newGameMessage.getMapContent();
            gameStateTracker.addNewGame(gameName, mapContent);
        });
    }

}

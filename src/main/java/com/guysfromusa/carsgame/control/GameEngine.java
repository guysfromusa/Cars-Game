package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
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

    private final ActiveGamesContainer activeGamesContainer;

    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    @Async
    public void handleMoves(List<Message> messages, String gameId) {
        GameState gameState = activeGamesContainer.getGameState(gameId);

        //TODO for all messages calculate movements and collisions

        //TODO store all state in DB

        //TODO update memory state

        messages.forEach(message -> {
            CompletableFuture<String> future = message.getFuture();
            log.info("Complete message");
            future.complete("{status:'OK'}");
        });
    }

}

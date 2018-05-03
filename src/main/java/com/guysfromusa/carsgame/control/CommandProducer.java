package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    //todo either? / optional / String?
    public String scheduleCommand(String gameName, Message move) {

         return Optional.ofNullable(activeGamesContainer.getGameState(gameName)) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<String> result = state.addMovementToExecute(move);
                    //TODO send notification
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse("{status:error}");
    }
}

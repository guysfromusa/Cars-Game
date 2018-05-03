package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.events.CommandEventPublisher;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    private final CommandEventPublisher commandEventPublisher;

    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer, CommandEventPublisher commandEventPublisher) {
        this.activeGamesContainer = activeGamesContainer;
        this.commandEventPublisher = commandEventPublisher;
    }

    //todo either? / optional / String?
    public String scheduleCommand(String gameName, Command move) {

         return Optional.ofNullable(activeGamesContainer.getGameState(gameName)) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<String> result = state.addCommandToExecute(move);
                    commandEventPublisher.fire(this);
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse("{status:error}");
    }
}

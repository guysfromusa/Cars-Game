package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
    }

    //todo either? / optional / String?
    public String scheduleCommand(String gameName, Command move) {

         return Optional.ofNullable(activeGamesContainer.getGameState(gameName)) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<String> result = state.addCommandToExecute(move, () -> "{status:error}");
                    applicationEventPublisher.publishEvent(new CommandEvent(this));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse("{status:error}");
    }

    public CarEntity scheduleCommand(AddCarToGameCommand cmd) {
        return Optional.ofNullable(activeGamesContainer.getGameState(cmd.getGameName()))
                .map(gameState -> {
                    CompletableFuture<CarEntity> result = gameState.addCommandToExecute(cmd, CarEntity::new);
                    applicationEventPublisher.publishEvent(new CommandEvent(this));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse(null);
    }
}

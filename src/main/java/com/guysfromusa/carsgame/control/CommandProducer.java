package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    private final ApplicationEventPublisher applicationEventPublisher;
    public ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();


    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
    }

    public List<CarDto> scheduleCommand(MoveCommand moveCommand) {
         return Optional.ofNullable(activeGamesContainer.getGameState(moveCommand.getGameName())) //could be the game is already finished
                .map(state -> {
                    //TODO emptyList is it right?
                    CompletableFuture<List<CarDto>> result = state.addCommandToExecute(moveCommand, Collections::emptyList);
                    applicationEventPublisher.publishEvent(new CommandEvent(this));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse(null);
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

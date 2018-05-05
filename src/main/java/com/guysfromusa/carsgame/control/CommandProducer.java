package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.services.MovementResultService;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final MovementResultService movementResultService;

    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer,
                           ApplicationEventPublisher applicationEventPublisher,
                           MovementResultService movementResultService) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.movementResultService = notNull(movementResultService);
    }

    //todo either? / optional / String?
    public List<Car> scheduleCommand(String gameName, Command move) {

         return Optional.ofNullable(activeGamesContainer.getGameState(gameName)) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<List<Car>> result = state.addCommandToExecute(move, ArrayList::new);
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

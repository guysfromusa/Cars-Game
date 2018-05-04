package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ApplicationEventPublisher applicationEventPublisher;

    private final CarService carService;

    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer,
                      ApplicationEventPublisher applicationEventPublisher,
                      CarService carService) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.carService = carService;
    }

    @Async
    public void handleMoves(List<Command> commands, String gameId) {
        GameState gameState = activeGamesContainer.getGameState(gameId);

        //TODO for all commands calculate movements and collisions

        //TODO store all state in DB

        //TODO update memory state

        commands.forEach(message -> {
            CompletableFuture<String> future = message.getFuture();
            log.info("Complete message");
            future.complete("{status:'OK'}");
        });

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }

    @Async
    public void handleAddCars(List<Command> commands, String gameName) { //TODO pass state
        GameState gameState = activeGamesContainer.getGameState(gameName);

        commands.forEach(command -> {
            AddCarToGameCommand cmd = (AddCarToGameCommand) command;
            CompletableFuture<CarEntity> result = cmd.getFuture();
            CarEntity carEntity = carService.addCarToGame(cmd.getCarName(), gameName, cmd.getStartingPoint());
            gameState.addNewCar(carEntity);
            result.complete(carEntity);

        });

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }
}

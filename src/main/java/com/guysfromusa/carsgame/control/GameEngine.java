package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import io.vavr.Tuple;
import io.vavr.control.Try;
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

    private final CarController carController;


    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer,
                      ApplicationEventPublisher applicationEventPublisher,
                      CarService carService, CarController carController) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.carService = carService;
        this.carController = carController;
    }

    @Async
    public void handleMoves(List<Command> commands, String gameId) {
        GameState gameState = activeGamesContainer.getGameState(gameId);

        //TODO for all commands calculate movements and collisions

        //TODO store all state in DB

        //TODO update memory state

        commands.forEach(message -> {
            String status = carController.calculateCarState((MoveCommand) message, gameState);

            CompletableFuture<String> future = message.getFuture();
            log.info("Complete message");
            future.complete(status);
        });

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }

    @Async
    public void handleAddCars(List<Command> commands, String gameName) {//TODO pass gameState
        GameState gameState = activeGamesContainer.getGameState(gameName);

        log.debug("Handle add commands: {}", commands);

        commands.stream()
                .map(command -> (AddCarToGameCommand) command)
                .map(cmd -> Tuple.of(cmd.getFuture(),
                        Try.of(() -> carService.addCarToGame(cmd.getCarName(), gameState, cmd.getStartingPoint()))
                                .toEither()))
                .forEach(tuple2 -> tuple2._2
                        .mapLeft(tuple2._1::completeExceptionally)
                        .right()
                        .forEach(carEntity -> {
                            gameState.addNewCar(carEntity);
                            tuple2._1.complete(carEntity);
                        }));

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }

}

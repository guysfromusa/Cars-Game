package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameMoveWatcher;
import io.vavr.Tuple;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

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

    private final CarMoveHandler carMoveHandler;

    private final GameMoveWatcher gameMoveWatcher;

    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer,
                      ApplicationEventPublisher applicationEventPublisher,
                      CarService carService,
                      CarMoveHandler carMoveHandler,
                      GameMoveWatcher gameMoveWatcher) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.carService = notNull(carService);
        this.carMoveHandler = notNull(carMoveHandler);
        this.gameMoveWatcher = notNull(gameMoveWatcher);
    }

    @Async
    public void handleMoves(List<Command> commands, String gameName) {
        GameState gameState = activeGamesContainer.getGameState(gameName);

        commands.stream()
                .map(command -> (MoveCommand)command)
                .map(moveCmd -> Tuple.of(moveCmd.getFuture(), new MoveData(gameState, moveCmd)))
                .forEach(moveData -> {
                    //TODO catch errors and completeExceptionally
                    carMoveHandler.handleMoveCommand(moveData._2);
                    moveData._1.complete(gameState.getAllCars());
                    gameState.updateLastMoveTimeStamp(moveData._1);
                });


        //TODO error handling

        //TODO store all state in DB

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

    @Async
    public void handleGameWatchCommand(List<Command> commands, String gameName){
        GameState gameState = activeGamesContainer.getGameState(gameName);

        commands.stream()
                .map(command -> (LastMoveWachCommand) command)
                .forEach(lastMoveWachCommand -> {
                    gameMoveWatcher.watchLastGameMoves(gameName);
                });

        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }
}

package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.commands.AddCarToGameCommand;
import com.guysfromusa.carsgame.control.commands.Command;
import com.guysfromusa.carsgame.control.commands.MoveCommand;
import com.guysfromusa.carsgame.control.commands.UndoCommand;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameMoveWatcher;
import com.guysfromusa.carsgame.services.UndoMovementService;
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

    private final UndoMovementService undoMovementService;

    private final GameMoveWatcher gameMoveWatcher;

    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer,
                      ApplicationEventPublisher applicationEventPublisher,
                      CarService carService,
                      CarMoveHandler carMoveHandler,
                      UndoMovementService undoMovementService,
                      GameMoveWatcher gameMoveWatcher) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.carService = notNull(carService);
        this.carMoveHandler = notNull(carMoveHandler);
        this.undoMovementService = notNull(undoMovementService);
        this.gameMoveWatcher = notNull(gameMoveWatcher);
    }

    @Async
    public void handleMoves(List<Command> commands, String gameName) {
        GameState gameState = activeGamesContainer.getGameState(gameName);
        //TODO to have full multi collision rewrite this to calculate all moves first and then resolve collisions
        commands.stream()
                .map(command -> (MoveCommand)command)
                .map(moveCmd -> Tuple.of(moveCmd.getFuture(), new MoveData(gameState, moveCmd)))
                .forEach(moveData -> {
                    carMoveHandler.handleMoveCommand(moveData._2);
                    moveData._1.complete(gameState.getAllCars());
                    gameState.updateLastMoveTimeStamp(moveData._1);
                });


        //TODO error handling

        //TODO store all state in DB

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent("GameEngine:handleMoves"));
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
        applicationEventPublisher.publishEvent(new CommandEvent("GameEngine:handleAddCars"));
    }

    @Async
    public void handleUndo(List<Command> commands, String gameName) {
        GameState gameState = activeGamesContainer.getGameState(gameName);
        log.debug("Handle undo commands: {}", commands);
        commands.stream()
                .map(command -> (UndoCommand) command)
                .map(cmd -> Tuple.of(cmd.getFuture(),
                        undoMovementService.doNMoveBack(gameName, cmd.getCarName(), cmd.getNumberOfStepBack())))
                .forEach(tuple2 -> {
                    tuple2._1.complete(tuple2._2);
                });

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent("GameEngine:handleUndo"));
    }

    @Async
    public void handleGameWatchCommand(List<Command> commands, String gameName){
        log.debug("Handle interrupt commands: {}", commands);
        commands.stream()
                .map(command -> (LastMoveWachCommand) command)
                .forEach(lastMoveWachCommand -> {
                    gameMoveWatcher.watchLastGameMoves(gameName);
                    lastMoveWachCommand.getFuture().complete("OK");
                });

        applicationEventPublisher.publishEvent(new CommandEvent("GameEngine:handleGameWatchCommand"));
    }
}

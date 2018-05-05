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

    @Inject
    public GameEngine(ActiveGamesContainer activeGamesContainer,
                      ApplicationEventPublisher applicationEventPublisher,
                      CarService carService,
                      CarMoveHandler carMoveHandler) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
        this.carService = notNull(carService);
        this.carMoveHandler = notNull(carMoveHandler);
    }

    @Async
    public void handleMoves(List<MoveCommand> commands, String gameId) {

        GameState gameState = activeGamesContainer.getGameState(gameId);

        commands.stream().map(moveCommand -> new MoveData(gameState, moveCommand))
                .forEach(carMoveHandler::handleMoveComand);

        //TODO store all state in DB

        gameState.setRoundInProgress(false);
        applicationEventPublisher.publishEvent(new CommandEvent(this));
    }

    @Async
    public void handleAddCars(List<Command> commands, String gameName) {//TODO pass gameState
        GameState gameState = activeGamesContainer.getGameState(gameName);

        commands.stream()
                .map(command -> (AddCarToGameCommand) command)
                .map(cmd -> Tuple.of(cmd.getFuture(),
                        Try.of(() -> carService.addCarToGame(cmd.getCarName(), gameName, cmd.getStartingPoint()))
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

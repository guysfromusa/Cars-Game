package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Point;
import io.vavr.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.API.run;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
@Slf4j
public class CarMoveHandler {

    private static final String CAR_CRASHED_MESSAGE= "Move cannot be made as car is already crashed.";

    private static final String CAR_CRASHED_INTO_WALL = "Car was crashed into wall";

    private static final String CAR_CRASHED_WITH_OTHER = "Car was crashed with other";

    private static final String CAR_NOT_IN_GAME = "Car not in game";

    private static final String CAR_IN_UNDO_PROCESS = "Car is in undo process";

    private static final String CAR_CANNOT_PERFORM_MOVE_DUE_TO_STEPS = "Car cannot perform move due to invalid number of steps";

    private final CollisionMonitor collisionMonitor;

    private final CarController carController;

    private final CarService carService;

    @Inject
    public CarMoveHandler(CollisionMonitor collisionMonitor, CarController carController, CarService carService){
        this.collisionMonitor = notNull(collisionMonitor);
        this.carController = notNull(carController);
        this.carService = notNull(carService);
    }

    public void handleMoveCommand(MoveData moveData) {
        checkCarAbilityToMove()
                .andThen(doMove())
                .andThen(checkCarCollisions())
                .andThen(doCarStateUpdate())
                .accept(moveData);
    }

    private Consumer<MoveData> checkCarAbilityToMove(){
        return moveData -> {
            CarDto car = moveData.getCar();
            Match(car).of(
                    Case($(Predicates.isNull()), () -> run(() -> {
                                log.debug("Car: {} is null", moveData.getMoveCommand().getCarName());
                                moveData.getFuture()
                                        .completeExceptionally(new IllegalArgumentException(CAR_NOT_IN_GAME));
                            }
                    )),
                    Case($(CarDto::isCrashed), () -> run(() -> {
                                log.debug("Car: {} is crashed", car.getName());
                                moveData.getFuture()
                                        .completeExceptionally(new IllegalArgumentException(CAR_CRASHED_MESSAGE));
                            }
                    )),
                    Case($(CarDto::isUndoInProcess), () -> run(() -> {
                                log.debug("Car: {} is in undo state", car.getName());
                                moveData.getFuture()
                                        .completeExceptionally(new IllegalArgumentException(CAR_IN_UNDO_PROCESS));
                            }
                    )),
                    Case($(c -> !c.getType().isValidStepsPerMove(moveData.getForwardSteps())), () -> run(() -> {
                                log.debug("Car: {} has invalid number of forward steps: {}", car.getName(), moveData.getForwardSteps());
                                moveData.getFuture()
                                        .completeExceptionally(new IllegalArgumentException(CAR_CANNOT_PERFORM_MOVE_DUE_TO_STEPS));
                            }
                    )),
                    Case($(), () -> run(() -> {
                        //do nothing in case car is able to perform move
                    }))
            );
        };
    }

    private Consumer<MoveData> doMove(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return ;
            }
            GameState gameState = moveData.getGameState();
            log.debug("Move handled: {}", moveData.getMoveCommand());
            log.debug("Car: {}", moveData.getCar());
            MoveResult moveResult = carController.moveCar(moveData.getMoveCommand(), gameState);
            log.debug("Move performed: {}", moveResult);
            markCrashedWhenCollision(moveResult.isWall(), moveData, future, CAR_CRASHED_INTO_WALL);
        };
    }

    private Consumer<MoveData> checkCarCollisions(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return;
            }
            List<CarDto> carsInGame = moveData.getCars();

            Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(carsInGame);
            log.debug("Crashed cars: {}", crashedCarNames);
            boolean carCrashedWithOther = isCarCrashedWithOther(crashedCarNames, moveData.getCar());
            log.debug("Car: {} crashed with cars: {}", moveData.getCar().getName(), crashedCarNames);
            markCrashedWhenCollision(carCrashedWithOther, moveData, future, CAR_CRASHED_WITH_OTHER);
        };
    }

    private Consumer<MoveData> doCarStateUpdate(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return;
            }
            log.debug("Move completed");
            future.complete(moveData.getCars());
        };
    }

    private void markCrashedWhenCollision(boolean isCollision, MoveData moveData, CompletableFuture future, String message){
        if(isCollision){
            CarDto car = moveData.getCar();
            GameState gameState = moveData.getGameState();
            car.setCrashed(true);
            removeFromGameMap(car);
            gameState.removeCar(car.getName());
            carService.crashAndRemoveFromGame(gameState.getGameName(), car);
            future.completeExceptionally(new IllegalArgumentException(message));
        }
    }

    private void removeFromGameMap(CarDto car) {
        Point position = car.getPosition();
        position.setY(null);
        position.setX(null);
        car.setGame(null);
    }

    private static boolean isCarCrashedWithOther(Set<String> crashedCars, CarDto handledCar){
        return crashedCars.contains(handledCar.getName());
    }

}

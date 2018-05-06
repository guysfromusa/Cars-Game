package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Point;
import io.vavr.Predicates;
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
public class CarMoveHandler {

    private static final String CAR_CRASHED_MESSAGE= "Move cannot be made as car is already crashed.";

    private static final String CAR_CRASHED_INTO_WALL = "Car was crashed into wall";

    private static final String CAR_CRASHED_WITH_OTHER = "Car was crashed with other";

    private static final String CAR_NOT_IN_GAME = "Car not in game";

    private static final String CAR_IN_UNDO_PROCESS = "Car is in undo process";

    private final CollisionMonitor collisionMonitor;

    private final CarController carController;

    @Inject
    public CarMoveHandler(CollisionMonitor collisionMonitor, CarController carController){
        this.collisionMonitor = notNull(collisionMonitor);
        this.carController = notNull(carController);
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
                    Case($(Predicates.isNull()), () -> run(() ->
                            moveData.getFuture()
                                    .completeExceptionally(new IllegalArgumentException(CAR_NOT_IN_GAME))
                    )),
                    Case($(CarDto::isCrashed), () -> run(() ->
                            moveData.getFuture()
                                    .completeExceptionally(new IllegalArgumentException(CAR_CRASHED_MESSAGE))
                    )),
                    Case($(CarDto::isUndoInProcess), () -> run(() ->
                            moveData.getFuture()
                                    .completeExceptionally(new IllegalArgumentException(CAR_IN_UNDO_PROCESS))
                    )),
                    Case($(), () -> run(() -> {
                        //do nothing in case car is able to perform move
                    }))
            );
            //TODO handle car is in undo
        };
    }

    private Consumer<MoveData> doMove(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return ;
            }
            GameState gameState = moveData.getGameState();
            MoveResult moveResult = carController.moveCar(moveData.getMoveCommand(), gameState);

            //TODO do it in better way, immutable car
            CarDto car = moveData.getCar();
            car.setPosition(moveResult.getNewPosition());
            car.setDirection(moveResult.getNewDirection());
            //mark car move in db

            markCrashedWhenCollision(moveResult.isWall(), car, future, CAR_CRASHED_INTO_WALL);
        };
    }

    private Consumer<MoveData> checkCarCollisions(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return;
            }
            List<CarDto> carsInGame = moveData.getCars();
            CarDto car = moveData.getCar();

            Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(carsInGame);
            boolean carCrashedWithOther = isCarCrashedWithOther(crashedCarNames, car);
            markCrashedWhenCollision(carCrashedWithOther, car, future, CAR_CRASHED_WITH_OTHER);
        };
    }

    private Consumer<MoveData> doCarStateUpdate(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            if(future.isDone()){
                return;
            }
            future.complete(moveData.getCars());
        };
    }

    private void markCrashedWhenCollision(boolean isCollision, CarDto car, CompletableFuture future, String message){
        if(isCollision){
            car.setCrashed(true);
            removeFromGameMap(car);
            //TODO mark car crashed in db
            future.completeExceptionally(new IllegalArgumentException(message));
        }
    }

    private void removeFromGameMap(CarDto car) {
        Point position = car.getPosition();
        position.setY(null);
        position.setX(null);
        car.setGame(null);
        //TODO mark car removed from map
    }

    private static boolean isCarCrashedWithOther(Set<String> crashedCars, CarDto handledCar){
        return crashedCars.contains(handledCar.getName());
    }

}

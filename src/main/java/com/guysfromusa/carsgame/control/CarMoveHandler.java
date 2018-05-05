package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class CarMoveHandler {

    private static final String CAR_CRASHED_MESSAGE= "Move cannot be made as car is already crashed.";

    private static final String CAR_CRASHED_INTO_WALL = "Car was crashed into wall";

    private static final String CAR_CRASHED_WITH_OTHER = "Car was crashed with other";

    private final CollisionMonitor collisionMonitor;

    private final CarController carController;

    @Inject
    public CarMoveHandler(CollisionMonitor collisionMonitor, CarController carController){
        this.collisionMonitor = notNull(collisionMonitor);
        this.carController = notNull(carController);
    }

    public void handleMoveComand(MoveData moveData){
        checkCarAbilityToMove()
                .andThen(doMove())
                .andThen(checkCarCollisions())
                .andThen(doCarStateUpdate())
                .accept(moveData);
    }

    private Consumer<MoveData> checkCarAbilityToMove(){
        return moveData -> {
            CarDto car = moveData.getCar();

            if(car.isCrashed()){
                moveData.getFuture().completeExceptionally(new IllegalArgumentException(CAR_CRASHED_MESSAGE));
            }
        };
    }

    private Consumer<MoveData> doMove(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getMoveCommand().getFuture();
            if(future.isDone()){
                return ;
            }
            GameState gameState = moveData.getGameState();
            boolean isCarCrashedIntoWall = carController.moveCar(moveData.getMoveCommand(), gameState);

            String message = CAR_CRASHED_INTO_WALL;
            CarDto car = moveData.getCar();
            markCrashedWhenCollision(isCarCrashedIntoWall, car, future, message);
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

            String message = CAR_CRASHED_WITH_OTHER;
            markCrashedWhenCollision(carCrashedWithOther, car, future, message);
        };
    }

    private Consumer<MoveData> doCarStateUpdate(){
        return moveData -> {
            CompletableFuture<List<CarDto>> future = moveData.getFuture();
            List<CarDto> cars = moveData.getCars();
            future.complete(cars);
        };
    }

    private void markCrashedWhenCollision(boolean isCollision, CarDto car, CompletableFuture future, String message){
        if(isCollision){
            car.setCrashed(true);
            removeFromGameMap(car);
            future.completeExceptionally(new IllegalArgumentException(message));
        }
    }

    private void removeFromGameMap(CarDto car) {
        Point position = car.getPosition();
        position.setY(null);
        position.setX(null);
        car.setGame(null);
    }

    public boolean isCarCrashedWithOther(Set<String> crashedCars, CarDto handledCar){
        return crashedCars.contains(handledCar.getName());
    }

}
package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
public class GameState {

    @Getter
    private final String gameName;

    @Getter
    private final Queue<Command> commandsQueue = new ConcurrentLinkedQueue<>();

    @Setter @Getter
    private volatile boolean roundInProgress = false;

    private Map<String, CarState> carsStatesMemory = new ConcurrentHashMap<>();

    public GameState(String gameName) {
        this.gameName = gameName;
    }

    public <T> CompletableFuture<T> addCommandToExecute(Command command, Supplier<T> errorCallback) {
        boolean added = commandsQueue.offer(command);
        return added ? command.getFuture() : completedFuture(errorCallback.get());
    }

    public void addMovementHistory(String carName, Movement.Operation operation) {
        Collection<Movement> carsMovement = carsStatesMemory.get(carName).getMovements();
        carsMovement.add(Movement.newMovement(operation));
    }

    //FIXME why do we need this maybe getOrDefault()?
    public void addNewCar(String carName, Point startingPoint) {
        Car car = Car.builder()
                .name(carName)
                .position(startingPoint).build();

        CarState carState = new CarState();
        carState.setCar(car);
        carsStatesMemory.put(carName, carState);
    }

    public Collection<Movement> getMovementHistory(String carName) {
        return Optional.ofNullable(carsStatesMemory.get(carName))
                .map(CarState::getMovements).orElse(null);
    }

    public Car getCar(String carName){
        return carsStatesMemory.get(carName).getCar();
    }
}

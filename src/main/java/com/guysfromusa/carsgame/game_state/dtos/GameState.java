package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

@Slf4j
public class GameState {

    @Getter
    private final String gameName;

    @Getter
    private final Integer[][] gameMapContent;

    @Getter
    private final Queue<Command> commandsQueue = new ConcurrentLinkedQueue<>();

    @Setter @Getter
    private volatile boolean roundInProgress = false;

    private Map<String, CarState> carsStatesMemory = new ConcurrentHashMap<>();

    public GameState(String gameName, Integer[][] gameMapContent) {
        this.gameName = gameName;
        this.gameMapContent = gameMapContent;
    }

    public <T> CompletableFuture<T> addCommandToExecute(Command command, Supplier<T> errorCallback) {
        boolean added = commandsQueue.offer(command);
        return added ? command.getFuture() : completedFuture(errorCallback.get());
    }

    public void addMovementHistory(String carName, Movement.Operation operation) {
        Collection<Movement> carsMovement = carsStatesMemory.get(carName).getMovements();
        carsMovement.add(Movement.newMovement(operation));
    }

    public void addNewCar(CarEntity carEntity) {
        Car car = Car.builder()
                .name(carEntity.getName())
                .position(new Point(carEntity.getPositionX(), carEntity.getPositionY())).build();

        CarState carState = new CarState();
        carState.setCar(car);
        carsStatesMemory.put(car.getName(), carState);
    }

    public Collection<Movement> getMovementHistory(String carName) {
        return Optional.ofNullable(carsStatesMemory.get(carName))
                .map(CarState::getMovements).orElse(null);
    }

    public CarState getCarState(String carName){
        return Optional.ofNullable(carsStatesMemory.get(carName)).orElse(null);
    }

    public Car getCar(String carName){
        return carsStatesMemory.get(carName).getCar();
    }

    public List<Car> getCarsInGame(){
        return carsStatesMemory.values().stream().map(CarState::getCar).collect(toList());
    }
}

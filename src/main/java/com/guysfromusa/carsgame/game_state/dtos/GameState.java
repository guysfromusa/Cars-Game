package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import static java.util.Collections.*;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

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

    public void addMovementHistory(String carName, MovementDto.Operation operation) {
        Collection<MovementDto> carsMovementDto = carsStatesMemory.get(carName).getMovementDtos();
        carsMovementDto.add(MovementDto.newMovementDto(operation));
    }

    public void addNewCar(CarEntity carEntity) {
        CarDto car = CarDto.builder()
                .name(carEntity.getName())
                .game(carEntity.getGame().getName())
                .position(new Point(carEntity.getPositionX(), carEntity.getPositionY())).build();

        CarState carState = new CarState();
        carState.setCar(car);
        carsStatesMemory.put(car.getName(), carState);
        log.info("Car: {} added to game: {}", car.getName(), carEntity.getGame().getName());
    }

    public List<CarDto> getAllCars(){
        return carsStatesMemory.values()
                .stream()
                .map(CarState::getCar)
                .collect(toList());
    }

    public Collection<MovementDto> getMovementHistory(String carName) {
        return Optional.ofNullable(carsStatesMemory.get(carName))
                .map(CarState::getMovementDtos).orElse(emptyList());
    }

    public CarDto getCar(String carName){
        return carsStatesMemory.get(carName).getCar();
    }

    public void setUndoProcessFlag(String carName, boolean value){
        carsStatesMemory.get(carName).setUndoInProcess(value);
    }
}

package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.v1.model.Car;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    @Getter
    private Integer[][] gameMapContent;
    private Map<String, CarState> carsMovementMap = new HashMap<>();

    public void addNewMovement(String carName, Movement.Operation operation) {
        List<Movement> carsMovement = carsMovementMap.get(carName).getMovements();
        carsMovement.add(Movement.newMovement(operation));
    }

    public void addNewCar(String carName) {
        CarState carState = CarState.newCarState();
        carState.getCar().setName(carName);
        carsMovementMap.put(carName, carState);
    }

    public CarState getCarState(String carName){
        return carsMovementMap.get(carName);
    }

    public List<Movement> getCarsMovement(String carName) {
        return carsMovementMap.get(carName).getMovements();
    }

    public String resolveCollision(Car car){
        //TODO check collitions and return status - might be collision info
        return StringUtils.EMPTY;
    }
}

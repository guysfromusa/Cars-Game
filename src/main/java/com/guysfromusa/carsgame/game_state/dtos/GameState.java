package com.guysfromusa.carsgame.game_state.dtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    private Map<String, List<Movement>> carsMovementMap = new HashMap<>();

    public void addNewMovement(String carName, Movement.Operation operation) {
        List<Movement> carsMovement = carsMovementMap.get(carName);
        carsMovement.add(Movement.newMovement(operation));
    }

    public void addNewCar(String carName) {
        carsMovementMap.put(carName, new ArrayList<>());
    }

    public List<Movement> getCarsMovement(String carName) {
        return carsMovementMap.get(carName);
    }
}

package com.guysfromusa.carsgame.game_state.dtos;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class GameState {

    private static Map<String, List<Movement>> CARS_MOVEMENT = new HashMap<>();

    public static void addNewMovement(String carName, Movement.Operation operation) {
        List<Movement> carsMovement = CARS_MOVEMENT.get(carName);
        carsMovement.add(Movement.newMovement(operation));
    }

    public static void addNewCar(String carName) {
        CARS_MOVEMENT.put(carName, new ArrayList<>());
    }

    public static List<Movement> getCarsMovement(String carName) {
        return CARS_MOVEMENT.get(carName);
    }
}

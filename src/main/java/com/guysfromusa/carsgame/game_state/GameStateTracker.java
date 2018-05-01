package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class GameStateTracker {
    private static Map<String, GameState> GAME_STATE = new HashMap<>();

    public static void addNewGame(String gameId){
        GAME_STATE.put(gameId, new GameState());
    }

    public static void addNewCar(String gameId, String carName){
       GameState gameState = GAME_STATE.get(gameId);
       gameState.addNewCar(carName);
    }

    public static void addNewMove(String gameId, String carName, Movement.Operation operation){
        GameState gameState = GAME_STATE.get(gameId);
        gameState.addNewMovement(carName, operation);
    }

    public static List<Movement> getCarsMovementHistory(String gameId, String carName){
       return  GAME_STATE.get(gameId).getCarsMovement(carName);
    }

    public static GameState getGameState(String gameId){
        return GAME_STATE.get(gameId);
    }
}

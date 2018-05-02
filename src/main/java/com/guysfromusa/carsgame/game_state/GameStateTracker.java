package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@Component
public class GameStateTracker {
    private Map<String, GameState> gameStateMap = new ConcurrentHashMap<>();

    public void addNewGame(String gameId){
        this.gameStateMap.put(gameId, new GameState());
    }

    public  void addNewCar(String gameId, String carName){
       GameState gameState = this.gameStateMap.get(gameId);
       gameState.addNewCar(carName);
    }

    public void addNewMove(String gameId, String carName, Movement.Operation operation){
        GameState gameState = this.gameStateMap.get(gameId);
        gameState.addNewMovement(carName, operation);
    }

    public List<Movement> getCarsMovementHistory(String gameId, String carName){
       return  this.gameStateMap.get(gameId).getCarsMovement(carName);
    }

    public GameState getGameState(String gameId){
        return this.gameStateMap.get(gameId);
    }
}

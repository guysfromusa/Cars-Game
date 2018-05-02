package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameStateTracker {
    private Map<String, GameState> GAME_STATE = new HashMap<>();

    public void addNewGame(String gameId){
        this.GAME_STATE.put(gameId, new GameState());
    }

    public  void addNewCar(String gameId, String carName){
       GameState gameState = this.GAME_STATE.get(gameId);
       gameState.addNewCar(carName);
    }

    public void addNewMove(String gameId, String carName, Movement.Operation operation){
        GameState gameState = this.GAME_STATE.get(gameId);
        gameState.addNewMovement(carName, operation);
    }

    public List<Movement> getCarsMovementHistory(String gameId, String carName){
       return  this.GAME_STATE.get(gameId).getCarsMovement(carName);
    }

    public GameState getGameState(String gameId){
        return this.GAME_STATE.get(gameId);
    }
}

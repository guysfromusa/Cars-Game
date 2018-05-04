package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ActiveGamesContainer {

    private Map<String, GameState> gameStateMap = new ConcurrentHashMap<>();

    public void addNewGame(String gameName){
        this.gameStateMap.put(gameName, new GameState(gameName));
        log.info("Game: {} added to container", gameName);
    }

    @Deprecated //use gameState directly
    public void addExecutedMove(String gameName, String carName, Movement.Operation operation){
        GameState gameState = this.gameStateMap.get(gameName);
        gameState.addMovementHistory(carName, operation);
    }

    @Deprecated //use gameState directly
    public Collection<Movement> getCarsMovementHistory(String gameName, String carName){
       return  this.gameStateMap.get(gameName).getMovementHistory(carName);
    }

    public GameState getGameState(String gameName){
        return this.gameStateMap.get(gameName);
    }

    public Collection<GameState> getGameStates(){
        return gameStateMap.values();
    }
}

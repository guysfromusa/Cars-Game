package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import javassist.NotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Lists.reverse;
import static java.lang.String.format;
import static java.util.Collections.emptyList;

/**
 * Created by Dominik Zurek 02.05.2018
 */

@NoArgsConstructor
@Component
public class GameStateTracker {
    private Map<String, GameState> gameStateMap = new ConcurrentHashMap<>();

    public void addNewGame(String gameId){
        this.gameStateMap.put(gameId, new GameState());
    }

    public  void addNewCar(String gameId, String carName) throws NotFoundException {
       GameState gameState = this.gameStateMap.get(gameId);
       if(gameState == null)
           throw new NotFoundException(format("Game with id {} dosn't exist", gameId));
       gameState.addNewCar(carName);
    }

    public void addNewMove(String gameId, String carName, Movement.Operation operation) throws NotFoundException {
        GameState gameState = this.gameStateMap.get(gameId);
        if(gameState == null)
            throw new NotFoundException(format("Game with id {} dosn't exist", gameId));
        gameState.addNewMovement(carName, operation);
    }

    public List<Movement> getCarsMovementHistory(String gameId, String carName){
        GameState gameState = this.gameStateMap.get(gameId);
        return gameState != null ? gameState.getCarsMovement(carName) : emptyList();
    }

    public GameState getGameState(String gameId){
        return this.gameStateMap.get(gameId);
    }

    public List<Movement> getNCarsMovementHistory(String gameId, String carName, int numberOfStepBack){
        GameState gameState = this.gameStateMap.get(gameId);
        List<Movement> carsMovement = emptyList();
        if(gameState != null){
           carsMovement = gameState.getCarsMovement(carName);
        }
        return  carsMovement.isEmpty() ? carsMovement :
                reverse(carsMovement.subList(carsMovement.size() - numberOfStepBack, carsMovement.size()));
    }
}

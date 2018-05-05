package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by Dominik Zurek 02.05.2018
 */
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

    public Optional<Collection<Movement>> getNCarsMovementHistory(String gameId, String carName, int numberOfStepBack){
        GameState gameState = this.gameStateMap.get(gameId);
        List<Movement> carsMovement = emptyList();
        if(gameState != null){
            carsMovement = newArrayList(gameState.getMovementHistory(carName));
        }

        return carsMovement.isEmpty() ? empty() :
                of(reverse(carsMovement.subList(carsMovement.size() - numberOfStepBack, carsMovement.size())));
    }

    public GameState getGameState(String gameName){
        return this.gameStateMap.get(gameName);
    }

    public Collection<GameState> getGameStates(){
        return gameStateMap.values();
    }
}

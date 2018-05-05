package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
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

    public Optional<Collection<MovementDto>> getNCarsMovementHistory(String gameId, String carName, int numberOfStepBack){
        GameState gameState = this.gameStateMap.get(gameId);
        List<MovementDto> carsMovementDto = emptyList();
        if(gameState != null){
            carsMovementDto = newArrayList(gameState.getMovementHistory(carName));
        }

        return carsMovementDto.isEmpty() ? empty() :
                of(reverse(carsMovementDto.subList(carsMovementDto.size() - numberOfStepBack, carsMovementDto.size())));
    }

    public GameState getGameState(String gameName){
        return this.gameStateMap.get(gameName);
    }

    public Collection<GameState> getGameStates(){
        return gameStateMap.values();
    }
}

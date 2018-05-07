package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Konrad Rys, 07.05.2018
 */

@Component
public class GameMoveWatcher {

    private final CarService carService;

    private final GameService gameService;

    private final ActiveGamesContainer activeGamesContainer;

    @Inject
    public GameMoveWatcher(CarService carService,
                           GameService gameService,
                           ActiveGamesContainer activeGamesContainer){
        this.carService = notNull(carService);
        this.gameService = notNull(gameService);
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    @Transactional
    public void watchLastGameMoves(String gameName){
        GameState gameState = activeGamesContainer.getGameState(gameName);
        boolean gameToBeFinished = gameState.isGameToBeFinished();
        if(!gameToBeFinished){
            return;
        }
        removeGame(gameName);
    }

    private void removeGame(String gameName) {
        carService.removeAllCarsFromGame(gameName);
        gameService.removeGame(gameName);
        activeGamesContainer.removeGame(gameName);
    }
}

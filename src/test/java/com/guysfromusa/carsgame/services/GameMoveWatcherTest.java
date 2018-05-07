package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Konrad Rys, 08.05.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class GameMoveWatcherTest {

    @Mock
    private CarService carService;

    @Mock
    private GameService gameService;

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @InjectMocks
    private GameMoveWatcher gameMoveWatcher;

    @Test
    public void whenGameToBeFinished_shouldRemoveGame(){
        //given
        String gameName = "GAME_NAME";
        boolean isGameToBeRemoved = true;

        GameState gameState = createGameState(isGameToBeRemoved);
        when(activeGamesContainer.getGameState(gameName)).thenReturn(gameState);

        //when
        gameMoveWatcher.watchLastGameMoves(gameName);

        //then
        verify(gameService).removeGame(eq(gameName));
        verify(carService).removeAllCarsFromGame(eq(gameName));
        verify(activeGamesContainer).removeGame(eq(gameName));

    }

    @Test
    public void whenGameNotToBeFinished_shouldNotRemoveGame(){
        //given
        String gameName = "GAME_NAME";
        boolean isGameToBeRemoved = false;

        GameState gameState = createGameState(isGameToBeRemoved);
        when(activeGamesContainer.getGameState(gameName)).thenReturn(gameState);

        //when
        gameMoveWatcher.watchLastGameMoves(gameName);

        //then
        verify(gameService, never()).removeGame(eq(gameName));
        verify(carService, never()).removeAllCarsFromGame(eq(gameName));
        verify(activeGamesContainer, never()).removeGame(eq(gameName));

    }

    private GameState createGameState(boolean isGameToBeRemoved) {
        GameState gameState = mock(GameState.class);
        when(gameState.isGameToBeFinished()).thenReturn(isGameToBeRemoved);
        return gameState;
    }

}
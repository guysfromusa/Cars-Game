package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.repositories.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepositoryMock;

    @Mock
    private MapService mapServiceMock;

    @Captor
    private ArgumentCaptor<GameEntity> gameEntityCaptor;

    @Test
    public void shouldStartNewGame() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setName("map1");
        when(gameRepositoryMock.findByName(any())).thenReturn(Optional.empty());
        when(mapServiceMock.find("map1"))
                .thenReturn(Optional.of(mapEntity));

        //when
        gameService.startNewGame("game1", "map1");

        //then
        verify(gameRepositoryMock).save(gameEntityCaptor.capture());
        GameEntity gotGame = gameEntityCaptor.getValue();

        assertThat(gotGame.getMap().getName()).isEqualTo("map1");
        assertThat(gotGame.getName()).isEqualTo("game1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfGameAlreadyExists() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setName("map1");
        when(gameRepositoryMock.findByName("game1"))
                .thenReturn(Optional.of(new GameEntity()));
        when(mapServiceMock.find("map1"))
                .thenReturn(Optional.of(mapEntity));


        //when
        gameService.startNewGame("game1", "map1");
    }

    @Test
    public void shouldReturnStatusOfTheGame() {
        //given
        GameEntity gameEntity = new GameEntity();
        String gameName = "game1";
        when(gameRepositoryMock.findByName(gameName))
                .thenReturn(Optional.of(gameEntity));

        //when
        GameStatus status = gameService.getStatus(gameName);

        //then
        assertThat(status).isEqualTo(GameStatus.RUNNING);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenGameNotFound() {
        //given
        String gameName = "game1";
        when(gameRepositoryMock.findByName(gameName))
                .thenReturn(Optional.empty());

        //when
        gameService.getStatus(gameName);
    }
}
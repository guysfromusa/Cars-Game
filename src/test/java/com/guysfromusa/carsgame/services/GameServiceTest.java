package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static com.guysfromusa.carsgame.entities.MapEntity.ACTIVE;
import static org.assertj.core.api.Java6Assertions.assertThat;
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
    private MapRepository mapRepositoryMock;

    @Captor
    private ArgumentCaptor<GameEntity> gameEntityCaptor;

    @Test
    public void shouldStartNewGame() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setName("map1");
        when(mapRepositoryMock.findByNameAndActive("map1", ACTIVE))
                .thenReturn(Optional.of(mapEntity));

        //when
        gameService.startNewGame("game1", "map1");

        //then
        verify(gameRepositoryMock).save(gameEntityCaptor.capture());
        GameEntity gotGame = gameEntityCaptor.getValue();

        assertThat(gotGame.getMap().getName()).isEqualTo("map1");
        assertThat(gotGame.getName()).isEqualTo("game1");
    }

}
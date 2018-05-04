package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static com.guysfromusa.carsgame.control.MessageType.ADD_CAR_TO_GAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Mikucki, 05.05.18
 */
@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {

    @Mock
    private CarService carService;

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private GameEngine gameEngine;


    @Test
    public void shouldAddCarToTheGame() {
        //given
        AddCarToGameCommand command = AddCarToGameCommand.builder()
                .carName("car1")
                .gameName("game1")
                .messageType(ADD_CAR_TO_GAME)
                .startingPoint(new Point(1, 1))
                .build();

        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");
        CarEntity addedCar = new CarEntity();
        addedCar.setName("car1");
        addedCar.setGame(gameEntity);
        addedCar.setPositionX(0);
        addedCar.setPositionY(0);

        when(carService.addCarToGame("car1", "game1", new Point(1, 1)))
                .thenReturn(addedCar);

        GameState gameStateMock = mock(GameState.class);
        when(gameStateMock.getGameName()).thenReturn("game1");

        when(activeGamesContainer.getGameState("game1"))
                .thenReturn(gameStateMock);


        //when
        gameEngine.handleAddCars(Collections.singletonList(command), "game1");

        //then
        CompletableFuture<CarEntity> future = command.getFuture();

        assertThat(future)
                .isCompletedWithValueMatching(carEntity -> carEntity.getName().equals("car1"))
                .isCompletedWithValueMatching(carEntity -> carEntity.getGame().getName().equals("game1"));

        verify(applicationEventPublisher).publishEvent(any());
        verify(gameStateMock).addNewCar(addedCar);
    }
}
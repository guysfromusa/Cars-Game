package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.commands.AddCarToGameCommand;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameMoveWatcher;
import com.guysfromusa.carsgame.services.UndoMovementService;
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

    @Mock
    private CarMoveHandler carMoveHandler;

    @Mock
    private UndoMovementService undoMovementService;

    @Mock
    private GameMoveWatcher gameMoveWatcher;

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
        addedCar.setGame(gameEntity);


        GameState gameState = new GameState("game1", null, 30);
        when(carService.addCarToGame("car1", gameState, new Point(1, 1)))
                .thenReturn(addedCar);

        when(activeGamesContainer.getGameState("game1"))
                .thenReturn(gameState);


        //when
        gameEngine.handleAddCars(Collections.singletonList(command), "game1");

        //then
        CompletableFuture<CarEntity> future = command.getFuture();

        assertThat(future)
                .isCompletedWithValueMatching(carEntity -> carEntity.getName().equals("car1"))
                .isCompletedWithValueMatching(carEntity -> carEntity.getGame().getName().equals("game1"));

        assertThat(gameState.getCar(addedCar.getName())).isNotNull();

        verify(applicationEventPublisher).publishEvent(any());

    }
}
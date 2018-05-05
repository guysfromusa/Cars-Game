package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Sets.newHashSet;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(MockitoJUnitRunner.class)
public class CarNotInGameValidatorTest {

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @InjectMocks
    private CarNotInGameValidator carNotInGameValidator;

    @Test
    public void shouldPassValidationWhenCarNotInGame() {
        //given
        GameEntity gameEntity = new GameEntity();
        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("carInGame1");
        carInGame1.setGame(gameEntity);
        gameEntity.setCars(newHashSet(carInGame1));
        gameEntity.setName("game1");
        CarEntity carToAdd = new CarEntity();
        carToAdd.setName("carToAdd");
        GameState gameState = new GameState("game1");
        gameState.addNewCar(carInGame1);
        when(activeGamesContainer.getGameState("game1")).thenReturn(gameState);
        CarGameAdditionValidationSubject validationSubject = CarGameAdditionValidationSubject.builder()
                .carEntity(carToAdd)
                .gameEntity(gameEntity)
                .gameState(gameState)
                .build();

        //when
        carNotInGameValidator.validate(validationSubject);
    }

    @Test
    public void shouldThrowExceptionWhenCarInGame() {
        //given
        GameEntity gameEntity = new GameEntity();
        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("carInGame1");
        carInGame1.setGame(gameEntity);
        gameEntity.setCars(newHashSet(carInGame1));
        gameEntity.setName("game1");
        CarEntity carToAdd = new CarEntity();
        carToAdd.setName("carInGame1");

        GameState gameState = new GameState("game1");
        gameState.addNewCar(carInGame1);
        when(activeGamesContainer.getGameState("game1")).thenReturn(gameState);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .carEntity(carToAdd)
                .gameEntity(gameEntity)
                .gameState(gameState)
                .build();

        //when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> carNotInGameValidator.validate(subject))
                .withMessage(CarNotInGameValidator.CAR_EXISTS_IN_GAME_MESSAGE);
    }
}
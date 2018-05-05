package com.guysfromusa.carsgame.validator;

import com.google.common.collect.Sets;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class StartingPointOccupiedValidatorTest {

    private StartingPointOccupiedValidator startingPointOccupiedValidator = new StartingPointOccupiedValidator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationWhenStartingPointNotOccupied() {
        //given
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");

        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("car1");
        carInGame1.setPositionX(0);
        carInGame1.setPositionY(1);
        carInGame1.setGame(gameEntity);

        CarEntity carInGame2 = new CarEntity();
        carInGame2.setName("car2");
        carInGame2.setPositionX(1);
        carInGame2.setPositionY(2);
        carInGame2.setGame(gameEntity);

        gameEntity.setCars(Sets.newHashSet(carInGame1, carInGame2));

        Point startingPoint = new Point(0, 2);

        GameState gameState = new GameState("game1");
        gameState.addNewCar(carInGame1);
        gameState.addNewCar(carInGame2);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .gameEntity(gameEntity)
                .gameState(gameState)
                .startingPoint(startingPoint)
                .build();

        //when
        startingPointOccupiedValidator.validate(subject);
    }

    @Test
    public void shouldThrowExceptionWhenStartingPointOccupied() {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(StartingPointOccupiedValidator.STARTING_POINT_OCCUPIED_MESSAGE);

        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");

        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("car1");
        carInGame1.setPositionX(0);
        carInGame1.setPositionY(0);
        carInGame1.setGame(gameEntity);

        CarEntity carInGame2 = new CarEntity();
        carInGame2.setName("car2");
        carInGame2.setPositionX(1);
        carInGame2.setPositionY(2);
        carInGame2.setGame(gameEntity);

        gameEntity.setCars(Sets.newHashSet(carInGame1, carInGame2));

        Point startingPoint = new Point(0, 0);

        GameState gameState = new GameState("game1");
        gameState.addNewCar(carInGame1);
        gameState.addNewCar(carInGame2);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .gameEntity(gameEntity)
                .gameState(gameState)
                .startingPoint(startingPoint)
                .build();
        //when
        startingPointOccupiedValidator.validate(subject);
    }
}
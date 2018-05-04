package com.guysfromusa.carsgame.validator;

import com.google.common.collect.Sets;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
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
        CarEntity carInGame1 = new CarEntity();
        carInGame1.setPositionX(0);
        carInGame1.setPositionY(1);

        CarEntity carInGame2 = new CarEntity();
        carInGame2.setPositionX(1);
        carInGame2.setPositionY(2);

        GameEntity gameEntity = new GameEntity();
        gameEntity.setCars(Sets.newHashSet(carInGame1, carInGame2));

        Point startingPoint = new Point(0, 2);

        CarGameAdditionValidationSubject carGameAdditionValidationSubject = new CarGameAdditionValidationSubject(null, gameEntity, startingPoint);

        //when
        startingPointOccupiedValidator.validate(carGameAdditionValidationSubject);
    }

    @Test
    public void shouldThrowExceptionWhenStartingPointOccupied() {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(StartingPointOccupiedValidator.STARTING_POINT_OCCUPIED_MESSAGE);

        CarEntity carInGame1 = new CarEntity();
        carInGame1.setPositionX(0);
        carInGame1.setPositionY(0);

        CarEntity carInGame2 = new CarEntity();
        carInGame2.setPositionX(1);
        carInGame2.setPositionY(2);

        GameEntity gameEntity = new GameEntity();
        gameEntity.setCars(Sets.newHashSet(carInGame1, carInGame2));

        Point startingPoint = new Point(0, 0);

        CarGameAdditionValidationSubject carGameAdditionValidationSubject = new CarGameAdditionValidationSubject(null, gameEntity, startingPoint);

        //when
        startingPointOccupiedValidator.validate(carGameAdditionValidationSubject);
    }
}
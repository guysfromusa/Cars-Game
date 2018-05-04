package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class CarNotInGameValidatorTest {

    private CarNotInGameValidator carNotInGameValidator = new CarNotInGameValidator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationWhenCarNotInGame() {
        //given
        GameEntity gameEntity = new GameEntity();
        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("carInGame1");
        gameEntity.setCars(newHashSet(carInGame1));
        CarEntity carToAdd = new CarEntity();
        carToAdd.setName("carToAdd");
        CarGameAdditionValidationSubject validationSubject = new CarGameAdditionValidationSubject(carToAdd, gameEntity, null);

        //when
        carNotInGameValidator.validate(validationSubject);
    }

    @Test
    public void shouldThrowExceptionWhenCarInGame() {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(CarNotInGameValidator.CAR_EXISTS_IN_GAME_MESSAGE);

        GameEntity gameEntity = new GameEntity();
        CarEntity carInGame1 = new CarEntity();
        carInGame1.setName("carInGame1");
        gameEntity.setCars(newHashSet(carInGame1));
        CarEntity carToAdd = new CarEntity();
        carToAdd.setName("carInGame1");
        CarGameAdditionValidationSubject validationSubject = new CarGameAdditionValidationSubject(carToAdd, gameEntity, null);

        //when
        carNotInGameValidator.validate(validationSubject);
    }
}
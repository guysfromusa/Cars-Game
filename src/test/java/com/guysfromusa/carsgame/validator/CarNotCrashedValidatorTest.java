package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class CarNotCrashedValidatorTest {

    private CarNotCrashedValidator carNotCrashedValidator = new CarNotCrashedValidator();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationWhenCarIsNotCrashed() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setCrashed(false);
        CarGameAdditionValidationSubject subject = new CarGameAdditionValidationSubject(carEntity, null, null);
        //when
        carNotCrashedValidator.validate(subject);
    }

    @Test
    public void shouldThrowExceptionWhenCarIsCrashed() {
        //given
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(CarNotCrashedValidator.CAR_CRASHED_MESSAGE);

        CarEntity carEntity = new CarEntity();
        carEntity.setCrashed(true);
        CarGameAdditionValidationSubject subject = new CarGameAdditionValidationSubject(carEntity, null, null);
        //when
        carNotCrashedValidator.validate(subject);
    }
}
package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class CarNotCrashedValidatorTest {

    private CarNotCrashedValidator carNotCrashedValidator = new CarNotCrashedValidator();

    @Test
    public void shouldPassValidationWhenCarIsNotCrashed() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setCrashed(false);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .carEntity(carEntity)
                .build();
        //when
        carNotCrashedValidator.validate(subject);
    }

    @Test
    public void shouldThrowExceptionWhenCarIsCrashed() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setCrashed(true);
        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .carEntity(carEntity)
                .build();

        //when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> carNotCrashedValidator.validate(subject))
                .withMessage(CarNotCrashedValidator.CAR_CRASHED_MESSAGE);
    }
}
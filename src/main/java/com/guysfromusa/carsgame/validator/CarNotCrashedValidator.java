package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.stereotype.Component;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class CarNotCrashedValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String CAR_CRASHED_MESSAGE = "Car is already crashed";

    @Override
    public void validate(CarGameAdditionValidationSubject carGameAdditionValidationSubject) {
        if(carGameAdditionValidationSubject.getCarEntity().isCrashed()){
            throw new IllegalArgumentException(CAR_CRASHED_MESSAGE);
        }
    }

}

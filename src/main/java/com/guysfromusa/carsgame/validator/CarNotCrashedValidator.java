package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Slf4j
@Component
public class CarNotCrashedValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String CAR_CRASHED_MESSAGE = "Car is already crashed";

    @Override
    public void validate(CarGameAdditionValidationSubject subject) {
        if(subject.getCarEntity().isCrashed()){
            log.debug("Car: {} crashed", subject.getCarEntity());
            throw new IllegalArgumentException(CAR_CRASHED_MESSAGE);
        }
    }

}

package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.stereotype.Component;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class StartingPointOccupiedValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    @Override
    public void validate(CarGameAdditionValidationSubject carGameAdditionValidationSubject) {
        //TODO
    }

}

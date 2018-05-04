package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.guysfromusa.carsgame.utils.StreamUtils.convert;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class CarNotInGameValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String CAR_EXISTS_IN_GAME_MESSAGE = "Car is already added to game";

    @Override
    public void validate(CarGameAdditionValidationSubject validationSubject) {
        List<String> gamesCarNames = convert(validationSubject.getGameEntity().getCars(), CarEntity::getName);
        String carName = validationSubject.getCarEntity().getName();
        if(gamesCarNames.contains(carName)){
            throw new IllegalArgumentException(CAR_EXISTS_IN_GAME_MESSAGE);
        }
    }
}

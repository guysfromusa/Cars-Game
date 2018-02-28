package com.guysfromusa.carsgame.v1.validators;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class CarGameAdditionValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return CarEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarEntity car = (CarEntity) target;
        Optional<Long> gameId = Optional.ofNullable(car).map(CarEntity::getGame).map(GameEntity::getId);

        if(gameId.isPresent()){
            errors.rejectValue("game", "car.assigned");
        }

        if(car.isCrashed()){
            errors.rejectValue("crashed", "now");
        }

        //TODO
        //add position validation on map

    }
}

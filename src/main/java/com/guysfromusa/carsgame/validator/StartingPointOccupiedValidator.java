package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
@Slf4j
public class StartingPointOccupiedValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String STARTING_POINT_OCCUPIED_MESSAGE = "Starting point is already occupied by another car";

    @Override
    public void validate(CarGameAdditionValidationSubject validationSubject) {
        Point startingPoint = validationSubject.getStartingPoint();
        GameState gameState = validationSubject.getGameState();

        List<Car> carsInGame = gameState.getAllCars();

        carsInGame.forEach(car -> {
            if (car.getPosition().equals(startingPoint)) {
                log.debug("Starting point occupied by car: {}", car);
                throw new IllegalArgumentException(STARTING_POINT_OCCUPIED_MESSAGE);
            }
        });
    }

}
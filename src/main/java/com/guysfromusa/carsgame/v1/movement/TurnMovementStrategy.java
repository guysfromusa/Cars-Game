package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Movement;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.v1.model.Movement.Type.TURN;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Component
public class TurnMovementStrategy implements MovementStrategy {

    private final CarService carService;

    @Inject
    public TurnMovementStrategy(CarService carService){
        this.carService = notNull(carService);
    }

    @Override
    public Movement.Type getType() {
        return TURN;
    }

    @Override
    public void execute(String game, String carName, Movement movement) {
        carService.turnCar(game, carName, movement.getTurnSide());
    }
}

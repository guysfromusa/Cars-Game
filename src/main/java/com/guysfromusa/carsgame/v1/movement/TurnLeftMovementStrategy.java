package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Component
    public class TurnLeftMovementStrategy implements MovementStrategy {

    @Override
    public Operation getType() {
        return Operation.LEFT;
    }

    @Override
    public void execute(Car car, Integer[][] mapContent) {
        Direction direction = car.getDirection().turnLeft();
        car.setDirection(direction);
    }
}

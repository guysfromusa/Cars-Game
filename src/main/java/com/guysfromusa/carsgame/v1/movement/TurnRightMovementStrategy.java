package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class TurnRightMovementStrategy implements MovementStrategy {

    @Override
    public Movement.Operation getType() {
        return RIGHT;
    }

    @Override
    public boolean execute(Car car, Integer[][] mapContent) {
        Direction direction = car.getDirection().turnRight();
        car.setDirection(direction);
        return true;
    }
}


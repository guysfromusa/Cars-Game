package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.Movement.Operation;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;

@Component
public class TurnRightMovementStrategy implements MovementStrategy {

    @Override
    public Operation getType() {
        return RIGHT;
    }

    @Override
    public void execute(Car car, Integer[][] mapContent) {
        Direction direction = car.getDirection().turnRight();
        car.setDirection(direction);
    }
}

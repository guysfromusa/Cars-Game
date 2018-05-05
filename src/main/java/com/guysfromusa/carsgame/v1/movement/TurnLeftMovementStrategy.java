package com.guysfromusa.carsgame.v1.movement;


import com.guysfromusa.carsgame.control.MoveStatus;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.stereotype.Component;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class TurnLeftMovementStrategy implements MovementStrategy {

    @Override
    public Movement.Operation getType() {
        return Movement.Operation.LEFT;
    }

    @Override
    public boolean execute(Car car, Integer[][] mapContent, Movement movement) {
        Direction direction = car.getDirection().turnLeft();
        car.setDirection(direction);
        return MoveStatus.SUCCESS.isMoved();
    }
}
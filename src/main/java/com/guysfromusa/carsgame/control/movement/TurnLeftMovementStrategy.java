package com.guysfromusa.carsgame.control.movement;


import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.model.Direction;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class TurnLeftMovementStrategy implements MovementStrategy {

    @Override
    public MovementDto.Operation getType() {
        return LEFT;
    }

    @Override
    public MoveResult execute(CarDto car, Integer[][] mapContent, MovementDto movement) {
        Direction direction = car.getDirection().turnLeft();
        return MoveResult
                .builder()
                .carName(car.getName())
                .newDirection(direction)
                .newPosition(car.getPosition())
                .wall(false)
                .moveStatus(SUCCESS)
                .build();
    }
}
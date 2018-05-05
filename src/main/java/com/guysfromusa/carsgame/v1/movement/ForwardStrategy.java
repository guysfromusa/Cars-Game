package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class ForwardStrategy implements MovementStrategy{

    @Override
    public Movement.Operation getType() {
        return FORWARD;
    }

    @Override
    public boolean execute(CarDto car, Integer[][] mapContent, Movement movement) {
        Point position = car.getPosition();

        Integer forwardSteps = movement.getForwardSteps();
        Integer xForwardPos = car.getDirection().getForwardXF().apply(position, forwardSteps);
        Integer yForwardPos = car.getDirection().getForwardYF().apply(position, forwardSteps);

        boolean carOnRoad = GameMapUtils.isPointOnRoad(mapContent, xForwardPos, yForwardPos);
        if(carOnRoad){
            updateCarPosition(car, xForwardPos, yForwardPos);
        }

        return carOnRoad;
    }

    private void updateCarPosition(CarDto car, Integer x, Integer y){
        car.getPosition().setY(y);
        car.getPosition().setX(x);
    }
}
package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.v1.model.Car;
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
    public boolean execute(Car car, Integer[][] mapContent) {
        Point position = car.getPosition();
        Integer xForwardPos = car.getDirection().getForwardXF().apply(position);
        Integer yForwardPos = car.getDirection().getForwardYF().apply(position);

        updateCarPosition(car, xForwardPos, yForwardPos);

        return GameMapUtils.isPointOnRoad(mapContent, position);
    }

    private void updateCarPosition(Car car, Integer x, Integer y){
        car.getPosition().setY(y);
        car.getPosition().setX(x);
    }
}
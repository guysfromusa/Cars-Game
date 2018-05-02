package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class ForwardStrategy implements MovementStrategy{

    private final MapService mapService;

    @Inject
    public ForwardStrategy(MapService mapService) {
        this.mapService = notNull(mapService);
    }

    @Override
    public Movement.Operation getType() {
        return Movement.Operation.FORWARD;
    }

    @Override
    public void execute(Car car, Integer[][] mapContent) {
        Point position = car.getPosition();
        Integer xForwardPos = car.getDirection().getForwardXF().apply(position);
        Integer yForwardPos = car.getDirection().getForwardYF().apply(position);

        boolean fieldReachableOnGameMap = mapService.isFieldReachableOnGameMap(mapContent, xForwardPos, yForwardPos);
        if(fieldReachableOnGameMap){
            updateCarPosition(car, xForwardPos, yForwardPos);
            return;
        }
        car.setCrashed(true);
    }

    private void updateCarPosition(Car car, Integer x, Integer y){
        car.getPosition().setY(y);
        car.getPosition().setX(x);
    }
}

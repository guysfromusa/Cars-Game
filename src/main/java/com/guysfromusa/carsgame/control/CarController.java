package com.guysfromusa.carsgame.control;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CarController {

    private final Map<Movement.Type, MovementStrategy> movementStrategyMap = Maps.newEnumMap(Movement.Type.class);

    //List<>

}

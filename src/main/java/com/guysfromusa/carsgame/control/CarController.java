package com.guysfromusa.carsgame.control;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.game_state.dtos.CarState;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation;

@Component
public class CarController {

    private final Map<Operation, MovementStrategy> movementStrategyMap = Maps.newEnumMap(Operation.class);

    @Inject
    public CarController(List<MovementStrategy> movementStrategies) {
        movementStrategies.forEach(movementStrategy ->
                movementStrategyMap.put(movementStrategy.getType(), movementStrategy));
    }

    public void calculateCarState(MovementMessage movementMessage, GameState gameState){

        Operation operation = movementMessage.getMovement().getOperation();
        CarState carState = gameState.getCarState(movementMessage.getCarName());

        Integer[][] gameMapContent = gameState.getGameMapContent();
        MovementStrategy movementStrategy = movementStrategyMap.get(operation);

        movementStrategy.execute(carState.getCar(), gameMapContent);

    }

}

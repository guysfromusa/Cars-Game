package com.guysfromusa.carsgame.control;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class CarController {

    private final Map<Movement.Operation, MovementStrategy> movementStrategyMap = Maps.newEnumMap(Movement.Operation.class);

    @Inject
    public CarController(List<MovementStrategy> movementStrategies) {
        movementStrategies.forEach(movementStrategy ->
                movementStrategyMap.put(movementStrategy.getType(), movementStrategy));
    }

    public boolean moveCar(MoveCommand movementMessage, GameState gameState){
        CarState carState = gameState.getCarState(movementMessage.getCarName());
        Car car = carState.getCar();

        Movement.Operation operation = movementMessage.getMovement().getOperation();

        Integer[][] gameMapContent = gameState.getGameMapContent();
        MovementStrategy movementStrategy = movementStrategyMap.get(operation);

        return movementStrategy.execute(car, gameMapContent);
    }

}


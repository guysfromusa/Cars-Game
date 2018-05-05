package com.guysfromusa.carsgame.control;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.control.movement.MovementStrategy;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
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

    public MoveResult moveCar(MoveCommand moveCmd, GameState gameState){
        CarState carState = gameState.getCarState(moveCmd.getCarName());
        CarDto car = carState.getCar();

        Movement.Operation operation = moveCmd.getMovement().getOperation();

        Integer[][] gameMapContent = gameState.getGameMapContent();
        MovementStrategy movementStrategy = movementStrategyMap.get(operation);

        Movement movement = moveCmd.getMovement();
        MoveResult moveResult = movementStrategy.execute(car, gameMapContent, movement);
        //TODO refactor game and car state
        gameState.addMovementHistory(car.getName(), operation);
        gameState.getCarState(moveResult.getCarName()).getMovements().add(movement);
        gameState.getCar(moveResult.getCarName()).setDirection(moveResult.getNewDirection());
        gameState.getCar(moveResult.getCarName()).setPosition(moveResult.getNewPosition());
        return moveResult;
    }

}


package com.guysfromusa.carsgame.control;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.control.commands.MoveCommand;
import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.control.movement.MovementStrategy;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.services.MovementsHistoryService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@Component
public class CarController {

    private final Map<MovementDto.Operation, MovementStrategy> movementStrategyMap = Maps.newEnumMap(MovementDto.Operation.class);

    private final MovementsHistoryService movementsHistoryService;

    @Inject
    public CarController(List<MovementStrategy> movementStrategies, MovementsHistoryService movementsHistoryService) {
        movementStrategies.forEach(movementStrategy ->
                movementStrategyMap.put(movementStrategy.getType(), movementStrategy));
        this.movementsHistoryService = movementsHistoryService;
    }

    public MoveResult moveCar(MoveCommand moveCmd, GameState gameState){
        CarState carState = gameState.getCarState(moveCmd.getCarName());
        CarDto car = carState.getCar();

        MovementDto.Operation operation = moveCmd.getMovementDto().getOperation();

        Integer[][] gameMapContent = gameState.getGameMapContent();
        MovementStrategy movementStrategy = movementStrategyMap.get(operation);

        MovementDto movement = moveCmd.getMovementDto();
        MoveResult moveResult = movementStrategy.execute(car, gameMapContent, movement);
        gameState.addMovementHistory(car.getName(), operation);
        car.setDirection(moveResult.getNewDirection());
        car.setPosition(moveResult.getNewPosition());
        movementsHistoryService.saveMove(gameState.getGameName(), moveResult);
        return moveResult;
    }

}


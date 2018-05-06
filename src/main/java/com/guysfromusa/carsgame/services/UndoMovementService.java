package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class UndoMovementService {

    private final UndoMovementPreparerService undoMovementPreparerService;
    private final MoveTaskCreator moveTaskCreator;

    @Autowired
    public UndoMovementService(UndoMovementPreparerService undoMovementPreparerService, MoveTaskCreator moveTaskCreator) {
        this.undoMovementPreparerService = notNull(undoMovementPreparerService);
        this.moveTaskCreator = notNull(moveTaskCreator);
    }

    public List<MovementDto> doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {

        //todo : add command which set flag as true and return List<movmentDto>
        List<MovementDto> movementDtos = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        undoMovementPreparerService.setUndoProcessFlag(gameId, carName, true);
        moveTaskCreator.schedule(1000L, new UndoState(gameId, carName, movementDtos));

        return movementDtos;
    }
}

class UndoState {

    @Getter
    final String gameName;
    @Getter
    final String carName;
    List<MovementDto> movementDtos;
    int currentMove = 0;

    UndoState(String gameName, String carName, List<MovementDto> movementDtos) {
        this.gameName = gameName;
        this.carName = carName;
        this.movementDtos = movementDtos;
    }


    synchronized MoveCommand createNextMove() {
        return new MoveCommand(gameName, carName, MessageType.MOVE, movementDtos.get(currentMove++), true);
    }

    synchronized boolean isLast() {
        return currentMove == movementDtos.size();
    }
}

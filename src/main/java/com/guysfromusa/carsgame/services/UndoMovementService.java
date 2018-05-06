package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class UndoMovementService {

    private final CommandProducer commandProducer;
    private final UndoMovementPreparerService undoMovementPreparerService;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public UndoMovementService(CommandProducer commandProducer, UndoMovementPreparerService undoMovementPreparerService, ScheduledExecutorService scheduler) {
        this.commandProducer = notNull(commandProducer);
        this.undoMovementPreparerService = notNull(undoMovementPreparerService);
        this.scheduler = notNull(scheduler);
    }

    public List<MovementDto> doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {
        List<MovementDto> movementDtos = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        undoMovementPreparerService.setUndoProcessFlag(gameId, carName, true);
        int delay = 1;
        for (MovementDto movementDto : movementDtos) {
            MoveCommand moveCommand = new MoveCommand(gameId, carName, MessageType.MOVE, movementDto, true);
            Runnable task = createTask(gameId, moveCommand);
            scheduler.schedule(task, delay, TimeUnit.SECONDS);
            delay++;
        }
        //todo : it has to be changed. I thnik it should be run when last move will be done or
        //when car will be crasehed
        undoMovementPreparerService.setUndoProcessFlag(gameId, carName, false);
        return movementDtos;

    }

    private Runnable createTask(String gameName, Command move) {
        return () -> commandProducer.scheduleCommand(gameName, move);
    }

}

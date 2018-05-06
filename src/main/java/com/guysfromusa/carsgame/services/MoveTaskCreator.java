package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MoveTaskCreator {

    private final CommandProducer commandProducer;
    private final UndoMovementPreparerService undoMovementPreparerService;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public MoveTaskCreator(CommandProducer commandProducer, UndoMovementPreparerService undoMovementPreparerService, ScheduledExecutorService scheduler) {
        this.commandProducer = commandProducer;
        this.undoMovementPreparerService = undoMovementPreparerService;
        this.scheduler = scheduler;
    }

    public void schedule(UndoState undoState) {
        Runnable task = () -> perfomMoveAndScheduleNext(undoState);
        //todo : timestamp
        scheduler.schedule(task, 1, TimeUnit.SECONDS);
    }

    private void perfomMoveAndScheduleNext(UndoState undoState) {
        MoveCommand undoMove = undoState.createNextMove();
        CarDto carDto = commandProducer.scheduleCommand(undoState.getGameName(), undoMove);
        if (carDto.isCrashed() || undoState.isLast()) {
            undoMovementPreparerService.setUndoProcessFlag(undoState.getGameName(), undoState.getCarName(), false);
        } else {
            schedule(undoState);
        }
    }
}

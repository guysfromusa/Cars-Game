package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
        Runnable task = () -> performMoveAndScheduleNext(undoState);
        //todo : timestamp
        scheduler.schedule(task, 1, TimeUnit.SECONDS);
    }

    private void performMoveAndScheduleNext(UndoState undoState) {
        MoveCommand undoMove = undoState.createNextMove();
        //TODO check if this is correct
        List<CarDto> carDto = commandProducer.scheduleCommand(undoMove);
        if (carDto.stream().anyMatch(CarDto::isCrashed) || undoState.isLast()) {
            undoMovementPreparerService.setUndoProcessFlag(undoState.getGameName(), undoState.getCarName(), false);
        } else {
            schedule(undoState);
        }
    }
}

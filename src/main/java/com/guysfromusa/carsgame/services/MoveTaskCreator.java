package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class MoveTaskCreator {

    private final CommandProducer commandProducer;
    private final UndoMovementPreparerService undoMovementPreparerService;
    private final ScheduledExecutorService scheduler;

    @Autowired
    public MoveTaskCreator(CommandProducer commandProducer, UndoMovementPreparerService undoMovementPreparerService, ScheduledExecutorService scheduler) {
        this.commandProducer = notNull(commandProducer);
        this.undoMovementPreparerService = notNull(undoMovementPreparerService);
        this.scheduler = notNull(scheduler);
    }

    public void schedule(UndoState undoState) {
        Timestamp before = new Timestamp(System.currentTimeMillis());
        Runnable task = () -> performMoveAndScheduleNext(undoState);
        Timestamp after = new Timestamp(System.currentTimeMillis());
        scheduler.schedule(task, 1 - (after.toLocalDateTime().getSecond() - before.toLocalDateTime().getSecond()), TimeUnit.SECONDS);
    }

    void performMoveAndScheduleNext(UndoState undoState) {
        MoveCommand undoMove = undoState.createNextMove();
        System.out.println("Helloo");
        CarDto carDto = commandProducer.scheduleCommand(undoState.getGameName(), undoMove);
        if (carDto == null || carDto.isCrashed() || undoState.isLast()) {
            undoMovementPreparerService.setUndoProcessFlag(undoState.getGameName(), undoState.getCarName(), false);
        } else {
            schedule(undoState);
        }
    }
}

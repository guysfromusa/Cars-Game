package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.commands.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.apache.commons.lang3.Validate.notNull;

@Component
@Slf4j
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

    public void schedule(long delayInMillis, UndoState undoState) {
        Runnable task = () -> performMoveAndScheduleNext(undoState);
        scheduler.schedule(task, delayInMillis, MILLISECONDS);
    }

    void performMoveAndScheduleNext(UndoState undoState) {
        long start = System.nanoTime();
        MoveCommand undoMove = undoState.createNextMove();

        List<CarDto> result = commandProducer.scheduleCommand(undoMove);

        log.debug("Move: {}", undoMove);
        log.debug("Result: {}", result);

        boolean isCrashed = result.stream()
                .filter(c -> undoState.getCarName().equals(c.getName()))
                .map(CarDto::isCrashed)
                .findFirst()
                .orElse(true);

        if (isCrashed || undoState.isLast()) {
            log.debug("Car '{}' crashed during undo", undoState.getCarName());
            undoMovementPreparerService.setUndoProcessFlag(undoState.getGameName(), undoState.getCarName(), false);
        } else {
            long delayInMillis = getDelayInMillis(start, System.nanoTime());
            schedule(delayInMillis, undoState);
        }
    }

    long getDelayInMillis(long start, long end) {
        long elapsed = NANOSECONDS.toMillis(end - start);
        long delay = 1000L - elapsed;
        return Math.max(0, delay); // scheduler accept negative delay but let be strict here
    }
}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

    public List<CarEntity> doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {
        List<Movement> movements = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        List<CompletableFuture<List<CarEntity>>> result = new LinkedList<>();
        MoveCommand moveCommand = new MoveCommand(gameId, carName, MessageType.MOVE);
        int delay = 1;
        for (Movement movement : movements) {
            moveCommand.setMovement(movement);
            Runnable task = createTask(gameId, moveCommand);
            scheduler.schedule(task, delay, TimeUnit.SECONDS);
            delay++;
            result.add(moveCommand.getFuture());
        }
        CompletableFuture<Void> allOfDone = CompletableFuture.allOf(result.toArray(new CompletableFuture[result.size()]));
        return allOfDone.thenApply(v -> result.get(result.size()-1)).get().get();

    }

    private Runnable createTask(String gameName, Command move) {
        return () -> commandProducer.scheduleCommand(gameName, move);
    }

}

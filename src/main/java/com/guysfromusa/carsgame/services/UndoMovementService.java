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
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class UndoMovementService {

    private final CommandProducer commandProducer;
    private final UndoMovementPreparerService undoMovementPreparerService;

    @Autowired
    public UndoMovementService(CommandProducer commandProducer, UndoMovementPreparerService undoMovementPreparerService) {
        this.commandProducer = notNull(commandProducer);
        this.undoMovementPreparerService = notNull(undoMovementPreparerService);
    }

    public List<CarEntity> doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {
        List<Movement> movements = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        List<CompletableFuture<List<CarEntity>>> result = new LinkedList<>();
        MoveCommand moveCommand = new MoveCommand(gameId, carName, MessageType.MOVE);
        for (Movement movement : movements) {
            moveCommand.setMovement(movement);
            Callable<CarEntity> task = createTask(gameId, moveCommand);
            commandProducer.scheduler.schedule(task, 1, TimeUnit.SECONDS);
            result.add(moveCommand.getFuture());
        }
        CompletableFuture<Void> allOfDone = CompletableFuture.allOf(result.toArray(new CompletableFuture[result.size()]));
        commandProducer.scheduler.shutdown();
        return allOfDone.thenApply(v -> result.get(result.size())).get().get();

    }

    private Callable<CarEntity> createTask(String gameName, Command move) {
        return () -> commandProducer.scheduleCommand(gameName, move);
    }

}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.GameController;
import com.guysfromusa.carsgame.control.Message;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class UndoMovementService {
    private final GameController gameController;
    private final UndoMovementPreparerService undoMovementPreparerService;

    @Autowired
    public UndoMovementService(GameController gameController, UndoMovementPreparerService undoMovementPreparerService) {
        this.gameController = notNull(gameController);
        this.undoMovementPreparerService = notNull(undoMovementPreparerService);
    }

    public List<String> doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {
        List<Movement> movements = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        List<String> result = new ArrayList<>();
        Message m = new Message();
        m.setCarName(carName);
        m.setGameName(gameId);
        ScheduledExecutorService scheduler = newSingleThreadScheduledExecutor();
        for (Movement movement : movements) {
            m.setMovement(movement);
            Callable<String> task = createTask(m);
            Future<String> schedule = scheduler.schedule(task, 1, TimeUnit.SECONDS);
            result.add(schedule.get());
        }
        return result;
    }

    private Callable<String> createTask(Message m) {
        return () -> {
            try {
                gameController.handle(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return m.getFuture().get();
        };
    }
}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.GameController;
import com.guysfromusa.carsgame.control.Message;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    //todo : test when resoroce will be finisd
    public String doNMoveBack(String gameId, String carName, int numberOfStepBack) throws InterruptedException, ExecutionException {
        List<Movement> movements = undoMovementPreparerService.prepareBackPath(gameId, carName, numberOfStepBack);
        Message m = new Message();
        m.setCarName(carName);
        m.setGameName(gameId);
        m.setMovements(movements);
        CompletableFuture<String> future = m.getFuture();
        gameController.handle(m);
        return future.get();
    }
}

package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.GameStateTracker;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class MessageDispatcher implements Runnable {

    private final GameStateTracker gameStateTracker;

    private final GameEngine gameEngine;

    private final TaskExecutor taskExecutor;

    public final CyclicBarrier queuesNotEmptyBarrier = new CyclicBarrier(2);

    @Autowired
    public MessageDispatcher(GameStateTracker gameStateTracker, GameEngine gameEngine, TaskExecutor taskExecutor) {
        this.gameStateTracker = notNull(gameStateTracker);
        this.gameEngine = notNull(gameEngine);
        this.taskExecutor = notNull(taskExecutor);
    }

    @PostConstruct
    public void init() {
        taskExecutor.execute(this);
    }

    @Override
    public void run() {
        while(true) {
            try {
                queuesNotEmptyBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                log.error("Barrier terminated: ", e);
            }

            Optional<GameState> gameToPlayRoundOptional = gameStateTracker.getGameStates().stream()
                    .filter(state -> !state.isRoundInProgress())
                    .filter(state -> !state.getMovementsQueue().isEmpty())
                    .findFirst();

            gameToPlayRoundOptional.ifPresent(gameState -> {
                gameState.setRoundInProgress(true);

                //FIXME filter messages by type and group different cars
                List<Message> consumedMessages = new ArrayList<>();
                gameState.getMovementsQueue().drainTo(consumedMessages);
                handle(MOVE, consumedMessages, gameState.getGameName());
            });

            if (!gameToPlayRoundOptional.isPresent()) {
                queuesNotEmptyBarrier.reset();
            }
        }
    }

    private void handle(MessageType messageType, List<Message> messages, String gameName) {
        //TODO Strategy someday
        switch (messageType) {
            case MOVE:
                gameEngine.handleMoves(messages, gameName);
                break;
            default:
                throw new IllegalStateException("Undefined message type");
        }
        gameStateTracker.getGameState(gameName).setRoundInProgress(false);
    }
}

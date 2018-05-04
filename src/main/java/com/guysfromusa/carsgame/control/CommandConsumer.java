package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class CommandConsumer {

    private final ActiveGamesContainer activeGamesContainer;

    private final GameEngine gameEngine;

    @Inject
    public CommandConsumer(ActiveGamesContainer activeGamesContainer, GameEngine gameEngine) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.gameEngine = notNull(gameEngine);
    }

    @EventListener(CommandEvent.class)
    public synchronized void handle(@SuppressWarnings("unused") CommandEvent event) {
        boolean queuesNotEmpty = true;

        while (queuesNotEmpty) {
            Optional<GameState> gameToPlayRoundOptional = activeGamesContainer.getGameStates().stream()
                    .filter(state -> !state.isRoundInProgress())
                    .filter(state -> !state.getCommandsQueue().isEmpty())
                    .findAny();

            queuesNotEmpty = gameToPlayRoundOptional.isPresent();

            gameToPlayRoundOptional.ifPresent(gameState -> {
                gameState.setRoundInProgress(true);

                //FIXME filter messages by type and group different cars
                List<Command> consumedCommands = new ArrayList<>();
                while (!gameState.getCommandsQueue().isEmpty()) {
                    consumedCommands.add(gameState.getCommandsQueue().poll());
                }
                handle(MOVE, consumedCommands, gameState.getGameName());
            });
        }
    }

    private void handle(MessageType messageType, List<Command> commands, String gameName) {
        //TODO Strategy someday
        switch (messageType) {
            case MOVE:
                gameEngine.handleMoves(commands, gameName);
                break;
            default:
                throw new IllegalStateException("Undefined message type");
        }
    }
}

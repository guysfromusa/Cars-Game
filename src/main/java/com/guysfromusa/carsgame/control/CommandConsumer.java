package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.commands.Command;
import com.guysfromusa.carsgame.control.round.GameRound;
import com.guysfromusa.carsgame.control.round.GameRoundSelector;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class CommandConsumer {

    private final ActiveGamesContainer activeGamesContainer;

    private final GameEngine gameEngine;

    private final GameRoundSelector gameRoundSelector;

    @Inject
    public CommandConsumer(ActiveGamesContainer activeGamesContainer, GameEngine gameEngine, GameRoundSelector gameRoundSelector) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.gameEngine = notNull(gameEngine);
        this.gameRoundSelector = notNull(gameRoundSelector);
    }

    @EventListener(CommandEvent.class)
    public synchronized void handle(@SuppressWarnings("unused") CommandEvent event) {
        if(log.isDebugEnabled()){
            log.debug("Handle command event: {}", event);
            log.debug("ActiveGames: {}", activeGamesContainer);
        }
        boolean queuesNotEmpty = true;

        while (queuesNotEmpty) {
            Optional<GameState> gameToPlayRoundOptional = activeGamesContainer.getGameStates().stream()
                    .filter(state -> !state.isRoundInProgress())
                    .filter(state -> !state.getCommandsQueue().isEmpty())
                    .findAny();

            log.debug("GameStateToPlayRound: {}", gameToPlayRoundOptional);

            queuesNotEmpty = gameToPlayRoundOptional.isPresent();

            gameToPlayRoundOptional.ifPresent(this::triggerRound);
        }
    }

    private void triggerRound(GameState gameState) {
        log.debug("Round triggered: {}", gameState.getGameName());
        gameState.setRoundInProgress(true);
        GameRound gameRound = gameRoundSelector.selectCommand(gameState.getCommandsQueue(), gameState.getGameName());
        handle(gameRound.getMessageType(), gameRound.getCommands(), gameRound.getGameName());
    }

    private void handle(MessageType messageType, List<Command> commands, String gameName) {
        log.debug("Handle round: {}, {}", messageType, commands);
        //TODO Strategy someday
        switch (messageType) {
            case MOVE:
                gameEngine.handleMoves(commands, gameName);
                break;
            case ADD_CAR_TO_GAME:
                gameEngine.handleAddCars(commands, gameName);
                break;
            case UNDO:
                gameEngine.handleUndo(commands, gameName);
                break;
            default:
                throw new IllegalStateException("Undefined message type");
        }
    }
}

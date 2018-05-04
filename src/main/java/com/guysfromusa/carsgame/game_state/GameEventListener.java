package com.guysfromusa.carsgame.game_state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
@Slf4j
public class GameEventListener {

    private final ActiveGamesContainer activeGamesContainer;

    @Inject
    public GameEventListener(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    @TransactionalEventListener
    @SuppressWarnings("unused")
    public void addNewGameListener(AddNewGameEvent addNewGameEvent){
        activeGamesContainer.addNewGame(addNewGameEvent.getGameName());
        log.info("Game {} added to container", addNewGameEvent.getGameName());
    }
}

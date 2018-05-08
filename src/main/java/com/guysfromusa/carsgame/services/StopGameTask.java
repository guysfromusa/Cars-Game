package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.LastMoveWachCommand;
import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Konrad Rys, 07.05.2018
 */

@Component
@Slf4j
public class StopGameTask {

    private final CommandProducer commandProducer;

    @Inject
    private ActiveGamesContainer activeGamesContainer;

    @Inject
    public StopGameTask(CommandProducer commandProducer) {
        this.commandProducer = notNull(commandProducer);
    }

    @Scheduled(fixedRate = 4000)
    public void doGameStateCheck(){
        log.debug("Check game state");
        LastMoveWachCommand command = LastMoveWachCommand.builder()
                .messageType(MessageType.STOP_GAME)
                .build();
        this.commandProducer.scheduleCommand(command);
    }
}

package com.guysfromusa.carsgame.control;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.control.commands.AddCarToGameCommand;
import com.guysfromusa.carsgame.control.commands.MoveCommand;
import com.guysfromusa.carsgame.control.commands.UndoCommand;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;

@Component
@Slf4j
public class CommandProducer {

    private final ActiveGamesContainer activeGamesContainer;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Inject
    public CommandProducer(ActiveGamesContainer activeGamesContainer,
                           ApplicationEventPublisher applicationEventPublisher) {
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.applicationEventPublisher = notNull(applicationEventPublisher);
    }

    //todo either? / optional / String?
    //FIXME CarDto::new
    public List<CarDto> scheduleCommand(MoveCommand move) {
        log.debug("Schedule command: {}", move);
        return Optional.ofNullable(activeGamesContainer.getGameState(move.getGameName())) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<List<CarDto>> result = state.addCommandToExecute(move, Collections::emptyList);
                    applicationEventPublisher.publishEvent(new CommandEvent("CommandProducer:scheduleCommand(MoveCommand)"));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse(emptyList());
    }

    //FIXME CarEntity::new to completeExceptionally
    public CarEntity scheduleCommand(AddCarToGameCommand addCmd) {
        log.debug("Schedule command: {}", addCmd);
        return Optional.ofNullable(activeGamesContainer.getGameState(addCmd.getGameName()))
                .map(gameState -> {
                    CompletableFuture<CarEntity> result = gameState.addCommandToExecute(addCmd, CarEntity::new);
                    applicationEventPublisher.publishEvent(new CommandEvent("CommandProducer:scheduleCommand(AddCarToGameCommand)"));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse(null);
    }

    public List<MovementDto> scheduleCommand(UndoCommand undoCommand) {
        log.debug("Schedule command: {}", undoCommand);
        return Optional.ofNullable(activeGamesContainer.getGameState(undoCommand.getGameName()))
                .map(gameState -> {
                    CompletableFuture<List<MovementDto>> result = gameState.addCommandToExecute(undoCommand, Collections::emptyList);
                    applicationEventPublisher.publishEvent(new CommandEvent("CommandProducer:scheduleCommand(UndoCommand)"));
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse(emptyList());
    }
}

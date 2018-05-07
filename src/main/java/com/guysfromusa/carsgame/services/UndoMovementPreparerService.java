package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.control.Try.run;
import static java.lang.String.valueOf;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek 02.05.2018
 */

@Component
@Slf4j
public class UndoMovementPreparerService {

    private final ActiveGamesContainer activeGamesContainer;


    @Autowired
    public UndoMovementPreparerService(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    public List<MovementDto> prepareBackPath(String gameId, String carName, int numberOfStepBack) {
        Collection<MovementDto> movementsHistory = activeGamesContainer.getNCarsMovementHistory(gameId, carName, numberOfStepBack);
        return !movementsHistory.isEmpty() ? inverseMovement(movementsHistory) : emptyList();
    }

    private List<MovementDto> inverseMovement(Collection<MovementDto> movementDtos) {
        List<MovementDto> backPath = new ArrayList<>();

        backPath.add(newMovementDto(LEFT));
        backPath.add(newMovementDto(LEFT));

        for (MovementDto movementDto : movementDtos) {
            Match(movementDto.getOperation()).of(
                    Case($(LEFT), run -> backPath.add(newMovementDto(RIGHT))),
                    Case($(RIGHT), run -> backPath.add(newMovementDto(LEFT))),
                    Case($(FORWARD), run -> backPath.add(newMovementDto(FORWARD))),
                    Case($(), o -> run(() -> {
                        throw new IllegalArgumentException(valueOf("Wrong operation of movementDto " + movementDto.getOperation()));
                    })));
        }

        backPath.add(newMovementDto(LEFT));
        backPath.add(newMovementDto(LEFT));

        return backPath;
    }

    public void setUndoProcessFlag(String gameId, String carName, boolean value) {
        log.debug("Set undo flag: game '{}', car: '{}' flag: '{}'", gameId, carName, value);
        activeGamesContainer.getGameState(gameId).setUndoProcessFlag(carName, value);
    }
}

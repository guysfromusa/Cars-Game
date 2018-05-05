package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.control.Try.run;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek 02.05.2018
 */

@Component
public class UndoMovementPreparerService {

    private final ActiveGamesContainer activeGamesContainer;


    @Autowired
    public UndoMovementPreparerService(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    public List<MovementDto> prepareBackPath(String gameId, String carName, int numberOfStepBack) {
        Optional<Collection<MovementDto>> movementsHistory = activeGamesContainer.getNCarsMovementHistory(gameId, carName, numberOfStepBack);
        return movementsHistory.isPresent() ? inverseMovement(movementsHistory.get()) : emptyList();
    }

    private List<MovementDto> inverseMovement(Collection<MovementDto> movementDtos) {
        List<MovementDto> backPath = new ArrayList<>();

        for (MovementDto movementDto : movementDtos) {
            MovementDto.Operation operation = movementDto.getOperation();
            Match(operation).of(
                    Case($(LEFT), run -> backPath.add(newMovementDto(RIGHT))),
                    Case($(RIGHT), run -> backPath.add(newMovementDto(LEFT))),
                    Case($(FORWARD), run -> backPath.addAll(asList(newMovementDto(LEFT), newMovementDto(LEFT), newMovementDto(FORWARD)))),
                    Case($(), o -> run(() -> {
                        throw new IllegalArgumentException(valueOf("Wrong operation of movementDto " + movementDto.getOperation()));
                    })));
        }
        backPath.addAll(asList(newMovementDto(LEFT), newMovementDto(LEFT)));
        return backPath;
    }
}

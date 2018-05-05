package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.newMovement;
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

    public List<Movement> prepareBackPath(String gameId, String carName, int numberOfStepBack) {
        Optional<Collection<Movement>> movementsHistory = activeGamesContainer.getNCarsMovementHistory(gameId, carName, numberOfStepBack);
        return movementsHistory.isPresent() ? inverseMovement(movementsHistory.get()) : emptyList();
    }

    private List<Movement> inverseMovement(Collection<Movement> movements) {
        List<Movement> backPath = new ArrayList<>();

        for (Movement movement : movements) {
            Movement.Operation operation = movement.getOperation();
            Match(operation).of(
                    Case($(LEFT), run -> backPath.add(newMovement(RIGHT))),
                    Case($(RIGHT), run -> backPath.add(newMovement(LEFT))),
                    Case($(FORWARD), run -> backPath.addAll(asList(newMovement(LEFT), newMovement(LEFT), newMovement(FORWARD)))),
                    Case($(), o -> run(() -> {
                        throw new IllegalArgumentException(valueOf("Wrong operation of movement " + movement.getOperation()));
                    })));
        }
        backPath.addAll(asList(newMovement(LEFT), newMovement(LEFT)));
        return backPath;
    }
}

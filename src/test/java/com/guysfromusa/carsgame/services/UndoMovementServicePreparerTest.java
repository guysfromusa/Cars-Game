package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.newMovement;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Dominik Zurek 02.05.2018
 */

@RunWith(MockitoJUnitRunner.class)
public class UndoMovementServicePreparerTest {

    private static final String carName = "toyota";
    private static final String gameId = "fifa";

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @InjectMocks
    private UndoMovementPreparerService service;

    @Test
    public void shouldPrepareBathPathFor2MoveBack(){
        //given
        when(activeGamesContainer.getNCarsMovementHistory(gameId, carName, 2))
                .thenReturn(of(asList(newMovement(RIGHT), newMovement(RIGHT))));

        //when
        List<Movement> result = service.prepareBackPath(gameId, carName, 2);

        //then
        assertThat(result)
                .extracting(Movement::getOperation)
                .containsExactly(LEFT, LEFT);
    }

    @Test
    public void shouldPrepareBathPathFor5MoveBack(){
        //given
        when(activeGamesContainer.getNCarsMovementHistory(gameId, carName, 2))
                .thenReturn(of(asList(newMovement(FORWARD), newMovement(RIGHT),
                        newMovement(FORWARD), newMovement(LEFT), newMovement(RIGHT))));

        //when
        List<Movement> result  = service.prepareBackPath(gameId, carName, 2);

        //then
        assertThat(result)
                .extracting(Movement::getOperation)
                .containsExactly(LEFT, LEFT, FORWARD, LEFT, LEFT, LEFT, FORWARD, RIGHT, LEFT);
    }
}
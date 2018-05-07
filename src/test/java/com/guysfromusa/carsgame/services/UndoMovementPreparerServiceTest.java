package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Dominik Zurek 02.05.2018
 */

@RunWith(MockitoJUnitRunner.class)
public class UndoMovementPreparerServiceTest {

    private static final String carName = "toyota";
    private static final String gameId = "fifa";

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @InjectMocks
    private UndoMovementPreparerService service;

    @Test
    public void shouldPrepareBathPathFor2MoveBack() {
        //given
        when(activeGamesContainer.getNCarsMovementHistory(gameId, carName, 2))
                .thenReturn(asList(newMovementDto(RIGHT), newMovementDto(RIGHT)));

        //when
        List<MovementDto> result = service.prepareBackPath(gameId, carName, 2);

        //then
        assertThat(result)
                .extracting(MovementDto::getOperation)
                .containsExactly(LEFT, LEFT, LEFT, LEFT, LEFT, LEFT);
    }

    @Test
    public void shouldPrepareBathPathFor5MoveBack() {
        //given
        when(activeGamesContainer.getNCarsMovementHistory(gameId, carName, 2))
                .thenReturn(asList(newMovementDto(FORWARD), newMovementDto(RIGHT),
                        newMovementDto(FORWARD), newMovementDto(LEFT), newMovementDto(RIGHT)));

        //when
        List<MovementDto> result = service.prepareBackPath(gameId, carName, 2);

        //then
        assertThat(result)
                .extracting(MovementDto::getOperation)
                .containsExactly(LEFT, LEFT, FORWARD, LEFT, FORWARD, RIGHT, LEFT, LEFT, LEFT);
    }
}
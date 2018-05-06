package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ScheduledExecutorService;

import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoveTaskCreatorTest {

    private static final String gameName = "fifa";
    private static final String carName = "mercedes";

    @InjectMocks
    private MoveTaskCreator moveTaskCreator;

    @Mock
    private CommandProducer commandProducer;

    @Mock
    private UndoMovementPreparerService undoMovementPreparerService;

    @Mock
    private ScheduledExecutorService scheduler;

    @Test
    public void shouldStopAfterFirstMove(){
        //given
        UndoState undoState = mock(UndoState.class);
        when(undoState.getGameName()).thenReturn(gameName);
        MoveCommand undoMove = new MoveCommand(gameName, carName, MOVE, newMovementDto(LEFT), true);
        when(undoState.createNextMove()).thenReturn(undoMove);
        when(commandProducer.scheduleCommand(undoState.getGameName(), undoMove))
                .thenReturn(CarDto.builder().crashed(true).build());
        //when
        moveTaskCreator.performMoveAndScheduleNext(undoState);

        //then
        verify(commandProducer, times(1)).scheduleCommand(undoState.getGameName(), undoMove);
    }

    @Test
    public void getDelayInMillis() {
        assertThat(moveTaskCreator.getDelayInMillis(0, 200_000_000)).isEqualTo(800L);
        assertThat(moveTaskCreator.getDelayInMillis(0, 1000_000_000)).isEqualTo(0L);
        assertThat(moveTaskCreator.getDelayInMillis(0, 2000_000_000)).isEqualTo(0L);
    }
}
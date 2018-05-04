package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Queue;

import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static com.guysfromusa.carsgame.game_state.dtos.GameStateBuilder.aGameState;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandConsumerTest {

    @InjectMocks
    private CommandConsumer commandConsumer;

    @Mock
    private ActiveGamesContainer activeGamesContainer;

    @Mock
    private GameEngine gameEngine;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void whenTriggeredByTheEvent_shouldCleanAllQueues() {
        //given
        List<GameState> gameStates = asList(
                aGameState()
                        .gameName("game1")
                        .roundInProgress(false)
                        .movementsQueue(new MoveCommand("g1", "c1", MOVE),
                                new MoveCommand("g1", "c1", MOVE))
                        .build(),
                aGameState()
                        .gameName("game2")
                        .roundInProgress(false)
                        .movementsQueue(new MoveCommand("g1", "c1", MOVE)).build());

        when(activeGamesContainer.getGameStates()).thenReturn(gameStates);
        when(activeGamesContainer.getGameState("game1")).thenReturn(gameStates.get(0));
        when(activeGamesContainer.getGameState("game2")).thenReturn(gameStates.get(1));

        //when
        commandConsumer.handle(new CommandEvent(this));

        //then
        assertThat(gameStates)
                .extracting(GameState::getCommandsQueue)
                .extracting(Queue::size)
                .containsExactly(0, 0);
    }
}
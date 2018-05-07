package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.commands.AddCarToGameCommand;
import com.guysfromusa.carsgame.control.commands.Command;
import com.guysfromusa.carsgame.control.commands.MoveCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static com.guysfromusa.carsgame.control.MessageType.ADD_CAR_TO_GAME;
import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@RunWith(MockitoJUnitRunner.class)
public class SameTypeOnePerCarGameRoundSelectorTest {

    @InjectMocks
    private SameTypeOnePerCarGameRoundSelector selector;

    @Test
    public void shouldThrowExceptionWhenQueueIsEmpty() {
        assertThatThrownBy(() -> selector.selectCommand(new LinkedList<>(), "game"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldSelectCommandOfTheSameMessageType() {
        MoveCommand moveCommand = new MoveCommand("game", "1", MOVE, newMovementDto(Operation.LEFT), false);
        AddCarToGameCommand addCarToGameCommand = AddCarToGameCommand.builder().carName("2")
                .gameName("game")
                .messageType(ADD_CAR_TO_GAME)
                .startingPoint(null)
                .build();

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(moveCommand);
        queue.add(addCarToGameCommand);

        GameRound gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isEqualTo(new GameRound("game", singletonList(moveCommand), MOVE));

        assertThat(queue).containsOnly(addCarToGameCommand);
    }

    @Test
    public void shouldSelectOneCommandPerCar() {
        MoveCommand firstMoveCarOne = new MoveCommand("game", "1", MOVE, newMovementDto(Operation.LEFT), false);
        MoveCommand firstMoveCarTwo = new MoveCommand("game", "2", MOVE, newMovementDto(Operation.LEFT), false);
        MoveCommand secondMoveCarOne = new MoveCommand("game", "1", MOVE, newMovementDto(Operation.LEFT), false);

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(firstMoveCarOne);
        queue.add(firstMoveCarTwo);
        queue.add(secondMoveCarOne);

        GameRound gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isEqualTo(new GameRound("game", asList(firstMoveCarOne, firstMoveCarTwo), MOVE));

        assertThat(queue).containsOnly(secondMoveCarOne);
    }

}
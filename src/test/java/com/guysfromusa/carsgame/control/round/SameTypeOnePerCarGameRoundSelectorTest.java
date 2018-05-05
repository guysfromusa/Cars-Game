package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.AddCarToGameCommand;
import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.MoveCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.Optional;

import static com.guysfromusa.carsgame.control.MessageType.ADD_CAR_TO_GAME;
import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@RunWith(MockitoJUnitRunner.class)
public class SameTypeOnePerCarGameRoundSelectorTest {

    @InjectMocks
    private SameTypeOnePerCarGameRoundSelector selector;

    @Test
    public void shouldReturnEmptyWhenQueueIsEmpty() {
        Optional<GameRound> gameRound = selector.selectCommand(new LinkedList<>(), "game");

        assertThat(gameRound).isEmpty();
    }

    @Test
    public void shouldSelectCommandOfTheSameMessageType() {
        MoveCommand moveCommand = new MoveCommand("game", "car", MOVE);
        AddCarToGameCommand addCarToGameCommand = AddCarToGameCommand.builder().carName("car")
                .gameName("game")
                .messageType(ADD_CAR_TO_GAME)
                .startingPoint(null)
                .build();

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(moveCommand);
        queue.add(addCarToGameCommand);

        Optional<GameRound> gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isPresent();
        assertThat(gameRound.get().getCommands()).containsOnly(moveCommand);
        assertThat(gameRound.get().getGameName()).isEqualTo("game");
        assertThat(gameRound.get().getMessageType()).isEqualTo(MOVE);

        assertThat(queue).containsOnly(addCarToGameCommand);
    }

    @Test
    public void shouldSelectOneCommandPerCar() {
        MoveCommand firstMoveCarOne = new MoveCommand("game", "1", MOVE);
        MoveCommand firstMoveCarTwo = new MoveCommand("game", "2", MOVE);
        MoveCommand secondMoveCarOne = new MoveCommand("game", "1", MOVE);

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(firstMoveCarOne);
        queue.add(firstMoveCarTwo);
        queue.add(secondMoveCarOne);

        Optional<GameRound> gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isPresent();
        assertThat(gameRound.get().getCommands()).containsOnly(firstMoveCarOne, firstMoveCarTwo);
        assertThat(gameRound.get().getGameName()).isEqualTo("game");
        assertThat(gameRound.get().getMessageType()).isEqualTo(MOVE);

        assertThat(queue).containsOnly(secondMoveCarOne);
    }

}
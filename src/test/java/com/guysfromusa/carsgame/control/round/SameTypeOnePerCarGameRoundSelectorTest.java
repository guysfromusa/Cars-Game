package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.AddCarToGameCommand;
import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.MoveCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

import static com.guysfromusa.carsgame.control.MessageType.ADD_CAR_TO_GAME;
import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static java.util.Arrays.asList;
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
        MoveCommand moveCommand = moveCommand("game", "1");
        AddCarToGameCommand addCarToGameCommand = AddCarToGameCommand.builder().carName("2")
                .gameName("game")
                .messageType(ADD_CAR_TO_GAME)
                .startingPoint(null)
                .build();

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(moveCommand);
        queue.add(addCarToGameCommand);

        GameRound gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isEqualTo(new GameRound("game", asList(moveCommand), MOVE));

        assertThat(queue).containsOnly(addCarToGameCommand);
    }

    @Test
    public void shouldSelectOneCommandPerCar() {
        MoveCommand firstMoveCarOne = moveCommand("game", "1");
        MoveCommand firstMoveCarTwo = moveCommand("game", "2");
        MoveCommand secondMoveCarOne = moveCommand("game", "1");

        LinkedList<Command> queue = new LinkedList<>();
        queue.add(firstMoveCarOne);
        queue.add(firstMoveCarTwo);
        queue.add(secondMoveCarOne);

        GameRound gameRound = selector.selectCommand(queue, "game");

        assertThat(gameRound).isEqualTo(new GameRound("game", asList(firstMoveCarOne, firstMoveCarTwo), MOVE));

        assertThat(queue).containsOnly(secondMoveCarOne);
    }

    private MoveCommand moveCommand(String game, String car){
        return MoveCommand.builder()
                .gameName(game)
                .carName(car)
                .messageType(MOVE)
                .build();
    }

}
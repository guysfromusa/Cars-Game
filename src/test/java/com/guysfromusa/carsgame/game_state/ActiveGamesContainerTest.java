package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import java.util.Collection;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class ActiveGamesContainerTest {

    private ActiveGamesContainer activeGamesContainer = new ActiveGamesContainer();

    @Test
    public void shouldAddNewGame(){
        //given
        activeGamesContainer.addNewGame("game1");

        //when
        activeGamesContainer.getGameState("game1");

        //then
        assertThat(activeGamesContainer.getCarsMovementHistory("game1", "someCar")).isNull();
    }

    @Test
    public void shouldAddNewCar(){
        //given
        activeGamesContainer.addNewGame("game1");

        //when
        activeGamesContainer.addNewCar("game1", "bmw");

        //then
        assertThat(activeGamesContainer.getCarsMovementHistory( "game1","bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.addNewCar("game1", "bmw");

        //when
        activeGamesContainer.addExecutedMove("game1","bmw", FORWARD);

        //then
        assertThat(activeGamesContainer.getGameState("game1")
                .getMovementHistory("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

    @Test
    public void shouldReturnTreeLastMove() {
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.addNewCar("game1", "bmw");
        activeGamesContainer.addExecutedMove("game1", "bmw", FORWARD);
        activeGamesContainer.addExecutedMove("game1", "bmw", LEFT);
        activeGamesContainer.addExecutedMove("game1", "bmw", FORWARD);
        activeGamesContainer.addExecutedMove("game1", "bmw", RIGHT);
        activeGamesContainer.addExecutedMove("game1", "bmw", RIGHT);
        //when
        Collection<Movement> result = activeGamesContainer.getNCarsMovementHistory("game1", "bmw", 3);

        //then
        assertThat(result)
                .extracting(Movement::getOperation)
                .containsExactly(RIGHT, RIGHT, FORWARD);
    }

    @Test
    public void shouldReturnEmpty(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.addNewCar("game1", "bmw");

        //when
        Collection<Movement> result = activeGamesContainer.getNCarsMovementHistory("fake", "bmw", 3);

        //then
        assertThat(result).isEmpty();
    }

}
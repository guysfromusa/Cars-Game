package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
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
        activeGamesContainer.addNewCar("game1", "bmw", null);

        //then
        assertThat(activeGamesContainer.getCarsMovementHistory( "game1","bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.addNewCar("game1", "bmw", null);

        //when
        activeGamesContainer.addExecutedMove("game1","bmw", FORWARD);

        //then
        assertThat(activeGamesContainer.getGameState("game1")
                .getMovementHistory("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

}
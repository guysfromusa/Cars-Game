package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import static com.guysfromusa.carsgame.game_state.GameStateTracker.addNewGame;
import static com.guysfromusa.carsgame.game_state.GameStateTracker.getGameState;
import static com.guysfromusa.carsgame.game_state.dtos.GameState.addNewCar;
import static com.guysfromusa.carsgame.game_state.dtos.GameState.addNewMovement;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTrackerTest {

    @Test
    public void shouldAddNewGame(){
        //given
        addNewGame("game1");

        //when
        GameState game1 = getGameState("game1");

        //then
        assertThat(game1.getCarsMovement("someCar")).isNull();
    }

    @Test
    public void shouldAddNewCar(){
        //given
        addNewGame("game1");

        //when
        addNewCar("bmw");

        //then
        assertThat(getGameState("game1").getCarsMovement("bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        addNewGame("game1");
        addNewCar("bmw");

        //when
       addNewMovement("bmw", FORWARD);

        //then
        Movement expected = Movement.newMovement(FORWARD);
        assertThat(getGameState("game1")
                .getCarsMovement("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

}
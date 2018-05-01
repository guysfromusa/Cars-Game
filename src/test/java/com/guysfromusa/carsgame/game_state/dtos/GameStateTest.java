package com.guysfromusa.carsgame.game_state.dtos;

import org.junit.Test;

import java.util.List;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTest {

    @Test
    public void shouldAddNewCarWithEmptyMovements(){
        //when
        GameState.addNewCar("bmw");

        //then
        List<Movement> bmwMovement = GameState.getCarsMovement("bmw");
        assertThat(bmwMovement).isEmpty();
    }

    @Test
    public void shouldAddNewMove(){
        //given
        GameState.addNewCar("bmw");

        //when
        GameState.addNewMovement("bmw", FORWARD);

        //then
        List<Movement> bmwMovement = GameState.getCarsMovement("bmw");
        assertThat(bmwMovement).extracting(Movement::getOperation).containsExactlyInAnyOrder(FORWARD);
    }

}
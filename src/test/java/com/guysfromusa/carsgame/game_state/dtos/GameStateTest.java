package com.guysfromusa.carsgame.game_state.dtos;

import org.junit.Test;

import java.util.Collection;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTest {

    private GameState gameState = new GameState("game1");

    @Test
    public void shouldAddNewCarWithEmptyMovements(){
        //when
        gameState.addNewCar("bmw");

        //then
        Collection<Movement> bmwMovement = gameState.getMovementHistory("bmw");
        assertThat(bmwMovement).isEmpty();
    }

    @Test
    public void shouldAddNewMove(){
        //given
        gameState.addNewCar("bmw");

        //when
        gameState.addMovementHistory("bmw", FORWARD);

        //then
        Collection<Movement> bmwMovement = gameState.getMovementHistory("bmw");
        assertThat(bmwMovement).extracting(Movement::getOperation).containsExactlyInAnyOrder(FORWARD);
    }

}
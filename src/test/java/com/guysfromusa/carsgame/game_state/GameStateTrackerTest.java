package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTrackerTest {

    private GameStateTracker gameStateTracker = new GameStateTracker();

    @Test
    public void shouldAddNewGame(){
        //given
        gameStateTracker.addNewGame("game1");

        //when
        gameStateTracker.getGameState("game1");

        //then
        assertThat(gameStateTracker.getCarsMovementHistory("game1", "someCar")).isNull();
    }

    @Test
    public void shouldAddNewCar(){
        //given
        gameStateTracker.addNewGame("game1");

        //when
        gameStateTracker.addNewCar("game1", "bmw");

        //then
        assertThat(gameStateTracker.getCarsMovementHistory( "game1","bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        gameStateTracker.addNewGame("game1");
        gameStateTracker.addNewCar("game1", "bmw");

        //when
        gameStateTracker.addNewMove("game1","bmw", FORWARD);

        //then
        assertThat(gameStateTracker.getGameState("game1")
                .getCarsMovement("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

}
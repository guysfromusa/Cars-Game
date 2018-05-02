package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import javassist.NotFoundException;
import org.junit.Test;

import java.util.List;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.RIGHT;
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
    public void shouldAddNewCar() throws NotFoundException {
        //given
        gameStateTracker.addNewGame("game1");

        //when
        gameStateTracker.addNewCar("game1", "bmw");

        //then
        assertThat(gameStateTracker.getCarsMovementHistory( "game1","bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement() throws NotFoundException {
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

    @Test(expected = NotFoundException.class)
    public void shouldTrowAnException() throws NotFoundException {
        //when
        gameStateTracker.addNewMove("game1","bmw", FORWARD);
    }

    @Test
    public void shouldReturnTreeLastMove() throws NotFoundException {
        //given
        gameStateTracker.addNewGame("game1");
        gameStateTracker.addNewCar("game1", "bmw");
        gameStateTracker.addNewMove("game1", "bmw", FORWARD);
        gameStateTracker.addNewMove("game1", "bmw", LEFT);
        gameStateTracker.addNewMove("game1", "bmw", FORWARD);
        gameStateTracker.addNewMove("game1", "bmw", RIGHT);
        gameStateTracker.addNewMove("game1", "bmw", RIGHT);
        //when
        List<Movement> result = gameStateTracker.getNCarsMovementHistory("game1", "bmw", 3);

        //then
        assertThat(result)
                .extracting(Movement::getOperation)
                .containsExactly(RIGHT, RIGHT, FORWARD);
    }

    @Test
    public void shouldReturnEmpty() throws NotFoundException {
        //given
        gameStateTracker.addNewGame("game1");
        gameStateTracker.addNewCar("game1", "bmw");

        //when
        List<Movement> result = gameStateTracker.getNCarsMovementHistory("fake", "bmw", 3);

        //then
        assertThat(result).isEmpty();
    }

}
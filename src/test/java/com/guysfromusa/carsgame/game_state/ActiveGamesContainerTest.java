package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
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
        assertThat(activeGamesContainer.getGameState("game1").getMovementHistory("someCar")).isNull();
    }

    @Test
    public void shouldAddNewCar(){
        //given
        activeGamesContainer.addNewGame("game1");

        //when
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //then
        assertThat(activeGamesContainer.getGameState("game1").getMovementHistory("bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //when
        activeGamesContainer.getGameState("game1").addMovementHistory("bmw", FORWARD);

        //then
        assertThat(activeGamesContainer.getGameState("game1")
                .getMovementHistory("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

    @Test
    public void shouldReturnTreeLastMove() {
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", FORWARD);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", LEFT);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", FORWARD);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", RIGHT);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", RIGHT);

        //when
        Optional<Collection<Movement>> result = activeGamesContainer.getNCarsMovementHistory("game1", "bmw", 3);

        //then
        assertThat(result.isPresent());
        assertThat(result.get())
                .extracting(Movement::getOperation)
                .containsExactly(RIGHT, RIGHT, FORWARD);
    }

    @Test
    public void shouldReturnEmpty(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //when
        Optional<Collection<Movement>> result = activeGamesContainer.getNCarsMovementHistory("fake", "bmw", 3);

        //then
        assertThat(!result.isPresent());
    }

}
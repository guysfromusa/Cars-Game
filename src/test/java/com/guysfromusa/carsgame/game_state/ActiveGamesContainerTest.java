package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import org.junit.Test;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
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
        assertThat(activeGamesContainer.getCarsMovementHistory("game1", "someCar")).isEmpty();
    }


    @Test
    public void shouldAddNewMovement(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //when
        activeGamesContainer.addExecutedMove("game1","bmw", FORWARD);

        //then
        assertThat(activeGamesContainer.getGameState("game1")
                .getMovementHistory("bmw"))
                .extracting(Movement::getOperation).containsExactly(FORWARD);
    }

}
package com.guysfromusa.carsgame.game_state;

import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import org.junit.Test;

import java.util.Collection;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.RIGHT;
import static org.assertj.core.api.Assertions.assertThat;

public class ActiveGamesContainerTest {

    private ActiveGamesContainer activeGamesContainer = new ActiveGamesContainer();

    @Test
    public void shouldAddNewGame(){
        //given
        activeGamesContainer.addNewGame("game1", null);

        //when
        activeGamesContainer.getGameState("game1");

        //then
        assertThat(activeGamesContainer.getGameState("game1").getMovementHistory("someCar")).isEmpty();
    }

    @Test
    public void shouldAddNewCar(){
        //given
        activeGamesContainer.addNewGame("game1", null);

        //when
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //then
        assertThat(activeGamesContainer.getGameState("game1").getMovementHistory("bmw")).isEmpty();
    }

    @Test
    public void shouldAddNewCarWithMovement(){
        //given
        activeGamesContainer.addNewGame("game1", null);
        activeGamesContainer.getGameState("game1")
                .addNewCar(aCarEntity().name("bmw").build());

        //when
        activeGamesContainer.getGameState("game1").addMovementHistory("bmw", FORWARD);

        //then
        assertThat(activeGamesContainer.getGameState("game1")
                .getMovementHistory("bmw"))
                .extracting(MovementDto::getOperation).containsExactly(FORWARD);
    }

    @Test
    public void shouldReturnTreeLastMove() {
        //given
        activeGamesContainer.addNewGame("game1", null);
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", FORWARD);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", LEFT);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", FORWARD);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", RIGHT);
        activeGamesContainer.getGameState("game1").addMovementHistory( "bmw", RIGHT);

        //when
        Collection<MovementDto> result = activeGamesContainer.getNCarsMovementHistory("game1", "bmw", 3);

        //then
        assertThat(result)
                .extracting(MovementDto::getOperation)
                .containsExactly(RIGHT, RIGHT, FORWARD);
    }

    @Test
    public void shouldReturnEmpty(){
        //given
        activeGamesContainer.addNewGame("game1", null);
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("bmw").build());

        //when
        Collection<MovementDto> result = activeGamesContainer.getNCarsMovementHistory("fake", "bmw", 3);

        //then
        assertThat(result.isEmpty());
    }

}
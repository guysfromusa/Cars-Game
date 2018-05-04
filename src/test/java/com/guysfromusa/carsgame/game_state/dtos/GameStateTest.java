package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import java.util.Collection;

import static com.guysfromusa.carsgame.game_state.dtos.Movement.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTest {

    private GameState gameState = new GameState("game1");

    @Test
    public void shouldAddNewCarWithEmptyMovements(){
        //when
        gameState.addNewCar("bmw", null);

        //then
        Collection<Movement> bmwMovement = gameState.getMovementHistory("bmw");
        assertThat(bmwMovement).isEmpty();
    }

    @Test
    public void shouldAddNewMove(){
        //given
        gameState.addNewCar("bmw", null);

        //when
        gameState.addMovementHistory("bmw", FORWARD);

        //then
        Collection<Movement> bmwMovement = gameState.getMovementHistory("bmw");
        assertThat(bmwMovement).extracting(Movement::getOperation).containsExactlyInAnyOrder(FORWARD);
    }

    @Test
    public void whenGivenStartingPoint_shouldStoreThisLocation(){
        //given
        Point startingPoint = new Point(1, 1);


        //when
        gameState.addNewCar("bmw", startingPoint);

        //then
        Car car = gameState.getCar("bmw");
        Point position = car.getPosition();
        assertThat(position)
                .extracting(Point::getX, Point::getY)
                .containsExactly(1, 1);
    }

}
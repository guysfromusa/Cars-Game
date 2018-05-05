package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import java.util.Collection;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static org.assertj.core.api.Assertions.assertThat;

public class GameStateTest {

    private GameState gameState = new GameState("game1");

    @Test
    public void shouldAddNewCarWithEmptyMovements(){
        //when
        gameState.addNewCar(aCarEntity().name("bmw").build());

        //then
        Collection<MovementDto> bmwMovement = gameState.getMovementHistory("bmw");
        assertThat(bmwMovement).isEmpty();
    }

    @Test
    public void shouldAddNewMove(){
        //given
        gameState.addNewCar(aCarEntity().name("bmw").build());

        //when
        gameState.addMovementHistory("bmw", FORWARD);

        //then
        Collection<MovementDto> bmwMovementDto = gameState.getMovementHistory("bmw");
        assertThat(bmwMovementDto).extracting(MovementDto::getOperation).containsExactlyInAnyOrder(FORWARD);
    }

    @Test
    public void whenGivenStartingPoint_shouldStoreThisLocation(){
        //given
        CarEntity carEntity = aCarEntity()
                .name("bmw")
                .positionX(1)
                .positionY(1).build();

        //when
        gameState.addNewCar(carEntity);

        //then
        Car car = gameState.getCar("bmw");
        Point position = car.getPosition();
        assertThat(position)
                .extracting(Point::getX, Point::getY)
                .containsExactly(1, 1);
    }

}
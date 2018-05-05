package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Movement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@RunWith(MockitoJUnitRunner.class)
public class TurnMovementStrategyTest {

    @InjectMocks
    private TurnLeftMovementStrategy turnMovementStrategy;

    @Mock
    private CarService carServiceMock;

    @Test
    public void shouldTurnCar() {
        //given
        Movement movement = new Movement();
        movement.setType(Movement.Type.TURN);
        movement.setTurnSide(TurnSide.RIGHT);

        //when
        turnMovementStrategy.execute(new CarDto("game1", "car1", movement);

        //then
        verify(carServiceMock).turnCar("game1", "car1", TurnSide.RIGHT);
    }
}
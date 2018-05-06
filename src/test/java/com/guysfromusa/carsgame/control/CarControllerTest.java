package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.control.movement.MovementStrategy;
import com.guysfromusa.carsgame.game_state.CarState;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.MovementsHistoryService;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.guysfromusa.carsgame.control.MessageType.MOVE;
import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.FORWARD;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.LEFT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation.RIGHT;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static com.guysfromusa.carsgame.model.Direction.EAST;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Mikucki, 06.05.18
 */
public class CarControllerTest {

    private MovementsHistoryService movementsHistoryService = mock(MovementsHistoryService.class);

    private MovementStrategy turnRight = mock(MovementStrategy.class);

    private MovementStrategy turnLeft = mock(MovementStrategy.class);

    private MovementStrategy forward = mock(MovementStrategy.class);

    private List<MovementStrategy> strategies = asList(turnLeft, turnRight, forward);

    private CarController carController;

    @Before
    public void setUp() {
        when(turnLeft.getType()).thenReturn(LEFT);
        when(turnRight.getType()).thenReturn(RIGHT);
        when(forward.getType()).thenReturn(FORWARD);
        carController = new CarController(strategies, movementsHistoryService);
    }

    @Test
    public void shouldReturnMoveResultAndSaveIntoDb() {
        //given
        MoveResult moveResult = MoveResult.builder()
                .carName("car1")
                .newDirection(EAST)
                .newPosition(new Point(1, 1))
                .moveStatus(SUCCESS)
                .wall(false)
                .build();

        Mockito.when(forward.execute(any(), any(), any())).thenReturn(moveResult);

        MoveCommand moveCmd = MoveCommand.builder()
                .messageType(MOVE)
                .movementDto(newMovementDto(FORWARD))
                .isUndo(false)
                .carName("car1")
                .gameName("game1")
                .build();

        GameState gameState = mock(GameState.class);
        CarState carState = mock(CarState.class);
        when(gameState.getCarState("car1")).thenReturn(carState);
        when(gameState.getGameName()).thenReturn("game1");
        CarDto carDto = mock(CarDto.class);
        when(carState.getCar()).thenReturn(carDto);
        when(gameState.getCar("car1")).thenReturn(carDto);

        //when
        MoveResult result = carController.moveCar(moveCmd, gameState);

        //then
        assertThat(result).isSameAs(moveResult);
        verify(movementsHistoryService).saveMove("game1", moveResult);
    }
}
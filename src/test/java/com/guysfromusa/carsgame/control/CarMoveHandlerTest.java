package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Konrad Rys, 06.05.2018
 */
@RunWith(MockitoJUnitRunner.class)
public class CarMoveHandlerTest {

    @Mock
    private CollisionMonitor collisionMonitor;

    @Mock
    private CarController carController;

    @InjectMocks
    private CarMoveHandler carMoveHandler;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void whenCarAlreadyCrashed_shouldReturnAlreadyCrashedMessage() throws ExecutionException, InterruptedException {
        //given
        boolean isCrashed = true;
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;

        MoveData moveData = createMoveData(isCrashed, xCarPosition, yCarPosition);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();

        //when
        carMoveHandler.handleMoveComand(moveData);

        //then
        exception.expectMessage("Move cannot be made as car is already crashed.");
        future.get();
    }

    private MoveData createMoveData(boolean isCrashed, Integer xCarPosition, Integer yCarPosition) {
        MoveData moveData = mock(MoveData.class);
        CompletableFuture<List<CarDto>> future = new CompletableFuture<>();

        when(moveData.getFuture()).thenReturn(future);

        CarDto carDto = CarDto.builder()
                .crashed(isCrashed)
                .position(new Point(xCarPosition, yCarPosition))
                .build();

        when(moveData.getCar()).thenReturn(carDto);

        return moveData;

    }


}
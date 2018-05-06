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
    public void whenCarNotInGame_shouldReturnCarNotInGameMessage() throws ExecutionException, InterruptedException {
        MoveData moveData = createMoveData(false, false, 0, 0);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Car not in game");
        future.get();
    }

    @Test
    public void whenCarAlreadyCrashed_shouldReturnAlreadyCrashedMessage() throws ExecutionException, InterruptedException {
        //given
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;

        MoveData moveData = createMoveData(true, true, xCarPosition, yCarPosition);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();

        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Move cannot be made as car is already crashed.");
        future.get();
    }

    private MoveData createMoveData(boolean carInGame, boolean isCrashed, Integer xCarPosition, Integer yCarPosition) {
        MoveData moveData = mock(MoveData.class);
        CompletableFuture<List<CarDto>> future = new CompletableFuture<>();

        when(moveData.getFuture()).thenReturn(future);

        if(carInGame){
            CarDto carDto = CarDto.builder()
                    .crashed(isCrashed)
                    .position(new Point(xCarPosition, yCarPosition))
                    .build();
            when(moveData.getCar()).thenReturn(carDto);
        }else{
            when(moveData.getCar()).thenReturn(null);
        }


        return moveData;

    }


}
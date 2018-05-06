package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
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

    @Test
    public void whenCarCollision_shouldReturnCollisionMessage() throws ExecutionException, InterruptedException {
        //given
        boolean isCrashed = false;
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;
        HashSet carsCollided = new HashSet(Arrays.asList("car1"));

        MoveData moveData = createMoveData(isCrashed, xCarPosition, yCarPosition);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        when(collisionMonitor.getCrashedCarNames(anyList())).thenReturn(carsCollided);

        MoveResult moveResult = createMoveResult();
        when(carController.moveCar(any(), any())).thenReturn(moveResult);
        //when
        carMoveHandler.handleMoveComand(moveData);

        //then
        exception.expectMessage("Car was crashed with other");
        future.get();
    }

    @Test
    public void whenCarMoveOk_shouldReturnCarList() throws ExecutionException, InterruptedException {
        //given
        boolean isCrashed = false;
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;
        HashSet carsCollided = new HashSet();

        MoveData moveData = createMoveData(isCrashed, xCarPosition, yCarPosition);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        MoveResult moveResult = createMoveResult();

        when(carController.moveCar(any(), any())).thenReturn(moveResult);
        when(collisionMonitor.getCrashedCarNames(anyList())).thenReturn(carsCollided);

        //when
        carMoveHandler.handleMoveComand(moveData);

        //then
        List<CarDto> carDtos = future.get();
        assertThat(carDtos).extracting(CarDto::getName).containsExactly("car1");

    }

    private MoveResult createMoveResult() {
        return MoveResult.builder()
                .newPosition(new Point(2, 4))
                .build();
    }

    private MoveData createMoveData(boolean isCrashed, Integer xCarPosition, Integer yCarPosition) {
        MoveData moveData = mock(MoveData.class);
        CompletableFuture<List<CarDto>> future = new CompletableFuture<>();

        when(moveData.getFuture()).thenReturn(future);

        CarDto carDto = CarDto.builder()
                .crashed(isCrashed)
                .name("car1")
                .position(new Point(xCarPosition, yCarPosition))
                .build();
        when(moveData.getCars()).thenReturn(Arrays.asList(carDto));
        when(moveData.getCar()).thenReturn(carDto);

        return moveData;

    }


}
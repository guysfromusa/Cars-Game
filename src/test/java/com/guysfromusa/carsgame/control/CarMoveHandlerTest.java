package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.services.CarService;
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
import static org.mockito.Mockito.verify;
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

    @Mock
    private CarService carService;

    @InjectMocks
    private CarMoveHandler carMoveHandler;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void whenCarNotInGame_shouldReturnCarNotInGameMessage() throws ExecutionException, InterruptedException {
        String carName = "car1";
        MoveData moveData = createMoveData(false, false, false, 0, 0, carName);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Car not in game");
        future.get();
    }

    @Test
    public void whenCarInUndoProcess_shouldReturnCarInUndoMessage() throws ExecutionException, InterruptedException {
        String carName = "car1";
        MoveData moveData = createMoveData(true, false, true, 0, 0, carName);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Car is in undo process");
        future.get();
    }

    @Test
    public void whenCarAlreadyCrashed_shouldReturnAlreadyCrashedMessage() throws ExecutionException, InterruptedException {
        //given
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;
        String carName = "car1";
        MoveData moveData = createMoveData(true, true, false, xCarPosition, yCarPosition, carName);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();

        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Move cannot be made as car is already crashed.");
        future.get();
    }

    @Test
    public void whenCarCollision_shouldReturnCollisionMessage() throws ExecutionException, InterruptedException {
        //given
        boolean isCrashed = false;
        String carName= "car1";
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;
        HashSet carsCollided = new HashSet(Arrays.asList("car1"));
        MoveData moveData = createMoveData(true, isCrashed, false, xCarPosition, yCarPosition, carName);
        GameState gameMock = mock(GameState.class);
        when(gameMock.getGameName()).thenReturn("game1");
        when(moveData.getGameState()).thenReturn(gameMock);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        when(collisionMonitor.getCrashedCarNames(anyList())).thenReturn(carsCollided);

        MoveResult moveResult = createMoveResult();
        when(carController.moveCar(any(), any())).thenReturn(moveResult);
        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        exception.expectMessage("Car was crashed with other");
        future.get();
        verify(gameMock).removeCar(carName);
        verify(carService).crashAndRemoveFromGame("game1", moveData.getCar());
    }

    @Test
    public void whenCarMoveOk_shouldReturnCarList() throws ExecutionException, InterruptedException {
        //given
        boolean isCrashed = false;
        String carName = "car1";
        Integer xCarPosition = 0;
        Integer yCarPosition = 0;
        HashSet carsCollided = new HashSet();

        MoveData moveData = createMoveData(true, isCrashed, false, xCarPosition, yCarPosition, carName);
        CompletableFuture<List<CarDto>> future = moveData.getFuture();
        MoveResult moveResult = createMoveResult();

        when(carController.moveCar(any(), any())).thenReturn(moveResult);
        when(collisionMonitor.getCrashedCarNames(anyList())).thenReturn(carsCollided);

        //when
        carMoveHandler.handleMoveCommand(moveData);

        //then
        List<CarDto> carDtos = future.get();
        assertThat(carDtos).extracting(CarDto::getName).containsExactly("car1");

    }

    private MoveResult createMoveResult() {
        return MoveResult.builder()
                .newPosition(new Point(2, 4))
                .build();
    }

    private MoveData createMoveData(boolean carInGame,
                                    boolean isCrashed,
                                    boolean undo,
                                    Integer xCarPosition,
                                    Integer yCarPosition,
                                    String carName) {
        MoveData moveData = mock(MoveData.class);
        CompletableFuture<List<CarDto>> future = new CompletableFuture<>();

        when(moveData.getFuture()).thenReturn(future);

        if(carInGame){
            CarDto carDto = CarDto.builder()
                    .crashed(isCrashed)
                    .position(new Point(xCarPosition, yCarPosition))
                    .name(carName)
                    .undoInProcess(undo)
                    .build();
            when(moveData.getCar()).thenReturn(carDto);
            when(moveData.getCars()).thenReturn(Arrays.asList(carDto));
        }else{
            when(moveData.getCar()).thenReturn(null);
        }


        return moveData;

    }


}
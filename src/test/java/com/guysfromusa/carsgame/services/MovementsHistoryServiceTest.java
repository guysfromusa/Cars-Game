package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import java.util.List;

import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.model.Direction.EAST;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovementsHistoryServiceTest {

    @Mock
    private MovementsHistoryRepository repository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private MovementsHistoryService movementsHistoryService;

    @Test
    public void shouldReturnEmptyList(){
        //given
        when(repository.findMovements(anyList(), anyList(), of(anyInt()))).thenReturn(emptyList());

        //when
        List<MovementHistory> result = movementsHistoryService.findMovementsHistory(emptyList(), emptyList(), of(2));

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldSaveNewMovement() {
        MoveResult moveResult = MoveResult.builder()
                .newDirection(EAST)
                .newPosition(new Point(1, 2))
                .wall(false)
                .carName("car1")
                .moveStatus(SUCCESS)
                .build();

        CarEntity carMock = mock(CarEntity.class);
        when(carRepository.findByGameAndName("game1", "car1")).thenReturn(of(carMock));

        GameEntity gameMock = mock(GameEntity.class);
        when(gameRepository.findByName("game1")).thenReturn(of(gameMock));

        //when
        movementsHistoryService.saveMove("game1", moveResult);

        //then
        ArgumentCaptor<MovementsHistoryEntity> moveCaptor = ArgumentCaptor.forClass(MovementsHistoryEntity.class);
        verify(repository).save(moveCaptor.capture());

        MovementsHistoryEntity result = moveCaptor.getValue();
        assertThat(result.getDirection()).isEqualTo(Direction.EAST);
        assertThat(result.getCar()).isEqualTo(carMock);
        assertThat(result.getGame()).isEqualTo(gameMock);
        assertThat(result.getPositionX()).isEqualTo(1);
        assertThat(result.getPositionY()).isEqualTo(2);

    }
}
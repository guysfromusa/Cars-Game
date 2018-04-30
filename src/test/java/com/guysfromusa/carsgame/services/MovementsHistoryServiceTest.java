package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import java.util.List;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static com.guysfromusa.carsgame.entities.GameEntityBuilder.aGameEntity;
import static com.guysfromusa.carsgame.entities.MovementsHistoryEntityBuilder.aMovementsHistoryEntity;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovementsHistoryServiceTest {

    @Mock
    private MovementsHistoryRepository repository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private MovementsHistoryService movementsHistoryService;

    @Test
    public void shouldReturnEmptyList(){
        //given
        when(repository.findMovements(anyList(), anyList(), of(anyInt()))).thenReturn(emptyList());
        when(conversionService.convert(any(), any())).thenCallRealMethod();

        //when
        List<MovementHistory> result = movementsHistoryService.findMovementsHistory(emptyList(), emptyList(), of(2));

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnMovementHistory(){
        //given
        MovementsHistoryEntity entity1 = aMovementsHistoryEntity()
                .car(aCarEntity().name("fiat").build())
                .game(aGameEntity().name("game1").build())
                .positionX(1)
                .positionY(2)
                .build();

        MovementsHistoryEntity entity2 = aMovementsHistoryEntity()
                .car(aCarEntity().name("mercedes").build())
                .game(aGameEntity().name("game2").build())
                .positionX(0)
                .positionY(0)
                .build();

        when(repository.findMovements(anyList(), anyList(), eq(of(2))))
                .thenReturn(asList(entity1, entity2));
        //when
        List<MovementHistory> result  = movementsHistoryService
                .findMovementsHistory(asList("game1", "game2"), asList("mercedes", "fiat"), of(2));

        //then


        assertThat(result).extracting(movementHistory -> movementHistory.getPosition().getX(),
                movementHistory -> movementHistory.getPosition().getY())
                .containsExactly(Tuple.tuple(1, 2),
                        Tuple.tuple(0 ,0));
    }
}
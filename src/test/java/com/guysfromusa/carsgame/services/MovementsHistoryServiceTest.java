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

import java.util.List;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static com.guysfromusa.carsgame.entities.GameEntityBuilder.aGameEntity;
import static com.guysfromusa.carsgame.entities.MovementsHistoryEntityBuilder.aMovementsHistoryEntity;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovementsHistoryServiceTest {

    @Mock
    private MovementsHistoryRepository repository;

    @InjectMocks
    private MovementsHistoryService movementsHistoryService;

    @Test
    public void shouldReturnEmptyList(){
        //given
        when(repository.findMovements(of(anyList()), of(anyList()), of(anyInt()))).thenReturn(emptyList());

        //when
        List<MovementHistory> result = movementsHistoryService.findMovementsHistory(of(emptyList()), of(emptyList()), of(2));

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnMovementHistory(){
        //given
        MovementsHistoryEntity entity1 = aMovementsHistoryEntity()
                .withCar(aCarEntity().withName("fiat").build())
                .withGame(aGameEntity().withName("game1").build())
                .withPositionX(1)
                .withPositionY(2)
                .build();

        MovementsHistoryEntity entity2 = aMovementsHistoryEntity()
                .withCar(aCarEntity().withName("mercedes").build())
                .withGame(aGameEntity().withName("game2").build())
                .withPositionX(0)
                .withPositionY(0)
                .build();

        when(repository.findMovements(of(anyList()), of(anyList()), eq(of(2))))
                .thenReturn(asList(entity1, entity2));
        //when
        List<MovementHistory> result  = movementsHistoryService
                .findMovementsHistory(of(asList("game1", "game2")), of(asList("mercedes", "fiat")), of(2));

        //then


        assertThat(result).extracting(movementHistory -> movementHistory.getPosition().getX(),
                movementHistory -> movementHistory.getPosition().getY())
                .containsExactly(Tuple.tuple(1, 2),
                        Tuple.tuple(0 ,0));
    }
}
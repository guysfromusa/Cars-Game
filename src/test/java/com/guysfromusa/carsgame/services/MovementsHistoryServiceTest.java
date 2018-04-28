package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
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

import static com.guysfromusa.carsgame.model.Direction.SOUTH;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
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
        when(repository.findMovements(anyList(), anyList(), anyInt())).thenReturn(emptyList());

        //when
        List<MovementHistory> result = movementsHistoryService.findMovementsHistory(emptyList(), emptyList(), 2);

        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnMovementHistory(){
        //given
        when(repository.findMovements(singletonList("game1"), singletonList("game2"), 2))
                .thenReturn(asList(build("game1","fiat", 1, 2),
                        build("game2","mercedes", 0, 0)));
        //when
        List<MovementHistory> result  = movementsHistoryService.findMovementsHistory(singletonList("game1"), singletonList("game2"), 2);

        //then
        assertThat(result).extracting(movementHistory -> movementHistory.getPosition().getX(),
                movementHistory -> movementHistory.getPosition().getY())
                .containsExactly(Tuple.tuple(1, 2),
                        Tuple.tuple(0 ,0));
    }

    private MovementsHistoryEntity build(String carName, String gameName, int posX, int posY){
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName(carName);
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName(gameName);
        MovementsHistoryEntity entity = new MovementsHistoryEntity();
        entity.setCar(carEntity);
        entity.setGame(gameEntity);
        entity.setPositionX(posX);
        entity.setPositionY(posY);
        entity.setDirection(SOUTH);

        return entity;
    }
}
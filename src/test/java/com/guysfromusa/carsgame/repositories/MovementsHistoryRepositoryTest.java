package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.assertj.core.groups.Tuple;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.model.Direction.SOUTH;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;


public class MovementsHistoryRepositoryTest extends BaseRepositoryTest {

    @Inject
    private MovementsHistoryRepository repository;

    @Test
    public void shouldSaveSingleMovement(){
        //given
        CarEntity car = new CarEntity();
        car.setName("fiat");
        GameEntity game = new GameEntity();
        game.setName("fifa");

        MovementsHistoryEntity toSave = new MovementsHistoryEntity();
        toSave.setGame(game);
        toSave.setCar(car);
        toSave.setDirection(SOUTH);
        toSave.setPositionX(1);
        toSave.setPositionY(3);

        //when
        MovementsHistoryEntity saved = repository.save(toSave);

        //then
        assertThat(saved.getCar().getName()).isEqualTo("fiat");
        assertThat(saved.getGame().getName()).isEqualTo("fifa");
        assertThat(saved.getPositionX()).isEqualTo(1);
        assertThat(saved.getPositionY()).isEqualTo(3);
        assertThat(saved.getDirection()).isEqualTo(SOUTH);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( emptyList(), singletonList("FIAT"), 0);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarAndMapName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( singletonList("FIFA"), singletonList("FIAT"), 0);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindOnlyLastMovements() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( singletonList("FIFA"), singletonList("FIAT"), 1);

        //then
        assertThat(result).hasSize(1).extracting(MovementsHistoryEntity::getPositionX, MovementsHistoryEntity::getPositionY).contains(Tuple.tuple(2, 2));
    }

    @Test
    public void shouldNotFindBecauseOfWrongGameId(){
        //when
        List<MovementsHistoryEntity> result = repository.findMovements(singletonList("NBA"), singletonList("FIAT"), 0);

        //then
        assertThat(result).isEmpty();
    }
}
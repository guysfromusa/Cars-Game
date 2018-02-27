package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
@Transactional
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
        MovementsHistoryEntity toSave = new MovementsHistoryEntity(game, car, 0, 0);

        //when
        MovementsHistoryEntity saved = repository.save(toSave);

        //then
        assertThat(saved.getCar().getName()).isEqualTo("fiat");
        assertThat(saved.getGame().getName()).isEqualTo("fifa");
        assertThat(saved.getPositionX()).isEqualTo(0);
        assertThat(saved.getPositionY()).isEqualTo(0);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( emptyList(), asList("FIAT"), 0);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarAndMapName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( asList("FIFA"), asList("FIAT"), 0);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindOnlyLastMovements() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( asList("FIFA"), asList("FIAT"), 1);

        //then
        assertThat(result).hasSize(1).extracting(MovementsHistoryEntity::getPositionX, MovementsHistoryEntity::getPositionY).contains(Tuple.tuple(2, 2));
    }

    @Test
    public void shouldNotFindBecauseOfWrongGameId(){
        //when
        List<MovementsHistoryEntity> result = repository.findMovements(asList("NBA"), asList("FIAT"), 0);

        //then
        assertThat(result).isEmpty();
    }
}
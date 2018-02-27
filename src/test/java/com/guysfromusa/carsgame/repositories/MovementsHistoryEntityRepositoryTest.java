package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static com.guysfromusa.carsgame.entities.MovementsHistoryEntity.withPosition;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
@Transactional
public class MovementsHistoryEntityRepositoryTest extends BaseRepositoryTest {

    @Inject
    private MovementsHistoryRepository repository;


    @Test
    public void shouldSaveSingleMovement(){
        //given
        CarEntity car = new CarEntity();
        car.setName("fiat");
        MovementsHistoryEntity toSave = new MovementsHistoryEntity(new GameEntity(), car, new Point(0,0));

        //when
        MovementsHistoryEntity saved = repository.save(toSave);

        //then
        assertThat(saved.getCar().getName()).isEqualTo("fiat");
        assertThat(saved.getPosition()).isEqualTo(new Point(0,0));
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindThreeByCarName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( emptyList(), asList("FIAT"), 0);

        //then
        assertThat(result).hasSize(1);
    }

    @Test
    public void shouldFindThreeByCarAndMapName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( emptyList(), emptyList(), 0);

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void shouldFindLastTwoMovements() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( emptyList(), emptyList(), 2);

        //then
      //  assertThat(result).hasSize(2).extracting(MovementsHistoryEntity::getPosition).contains(THRID_POINT, SECOND_POINT);
    }

    @Test
    public void shouldNotFindBecauseOfWrongGameId(){
        //when
     //   List<MovementsHistoryEntity> result = repository.findMovements("4", MAP_NAME_1, CAR_NAME_1, 2);

        //then
      //  assertThat(result).isEmpty();
    }
}
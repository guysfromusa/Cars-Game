package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.entities.MovementsHistoryEntity.withPosition;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
public class MovementsHistoryEntityRepositoryTest {

    private static final String GAME_ID_1 = "firstGame";
    private static final String MAP_NAME_1 = "krakow";
    private static final String CAR_NAME_1 = "fiat126p";
    private static final Point FIRST_POINT = new Point(0,0);
    private static final Point SECOND_POINT = new Point(1,1);
    private static final Point THRID_POINT = new Point(2,2);

    @Inject
    private MovementsHistoryRepository repository;

    @Before
    public void addToDataBase(){
        MovementsHistoryEntity firstPos = new MovementsHistoryEntity(GAME_ID_1, MAP_NAME_1, CAR_NAME_1, FIRST_POINT);
        MovementsHistoryEntity secondPos = withPosition(firstPos, SECOND_POINT);
        MovementsHistoryEntity thirdPos = withPosition(secondPos, THRID_POINT);
        List<MovementsHistoryEntity> toSave = asList(firstPos, secondPos, thirdPos);

        repository.save(toSave);
    }

    @Test
    public void shouldSaveSingleMovement(){
        //given
        MovementsHistoryEntity toSave = new MovementsHistoryEntity("someGame", "tokyo", "toyota", new Point(0,0));

        //when
        MovementsHistoryEntity saved = repository.save(toSave);

        //then
        assertThat(saved.getCarName()).isEqualTo("toyota");
        assertThat(saved.getMapName()).isEqualTo("tokyo");
        assertThat(saved.getGameId()).isEqualTo(-1L);
        assertThat(saved.getPosition()).isEqualTo(new Point(0,0));
    }

    @Test
    public void shouldFindThreeByCarName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements("", "", CAR_NAME_1, 0);

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void shouldFindThreeByCarAndMapName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements("", MAP_NAME_1, CAR_NAME_1, 0);

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void shouldFindLastTwoMovements() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements("", MAP_NAME_1, CAR_NAME_1, 2);

        //then
        assertThat(result).hasSize(2).extracting(MovementsHistoryEntity::getPosition).contains(THRID_POINT, SECOND_POINT);
    }

    @Test
    public void shouldNotFindBecauseOfWrongGameId(){
        //when
        List<MovementsHistoryEntity> result = repository.findMovements("4", MAP_NAME_1, CAR_NAME_1, 2);

        //then
        assertThat(result).isEmpty();
    }

}
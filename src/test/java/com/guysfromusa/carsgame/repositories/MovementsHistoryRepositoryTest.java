package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static com.guysfromusa.carsgame.entities.GameEntityBuilder.aGameEntity;
import static com.guysfromusa.carsgame.entities.MovementsHistoryEntityBuilder.aMovementsHistoryEntity;
import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
import static com.guysfromusa.carsgame.model.Direction.SOUTH;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


public class MovementsHistoryRepositoryTest extends BaseRepositoryTest {

    @Inject
    private MovementsHistoryRepository repository;

    @Test
    public void shouldSaveSingleMovement(){
        //given
        CarEntity car = aCarEntity()
                .withName("fiat")
                .withCrashed(false)
                .withPositionX(1)
                .withPositionY(3)
                .withCarType(NORMAL)
                .build();

        GameEntity game = aGameEntity()
                .withName("fifa")
                .withCars(newHashSet(car))
                .withMap(new MapEntity("map1", "1,1,1"))
                .build();

        MovementsHistoryEntity toSave = aMovementsHistoryEntity()
                .withCar(car)
                .withGame(game)
                .withPositionX(1)
                .withPositionY(3)
                .withDirection(SOUTH)
                .build();
        //when
        MovementsHistoryEntity saved = repository.save(toSave);

        //then
        assertThat(saved.getCar())
                .extracting(CarEntity::getName, CarEntity::getCarType, CarEntity::isCrashed, CarEntity::getPositionX, CarEntity::getPositionY)
                .containsExactly("fiat", NORMAL, false, 1, 3);
        assertThat(saved.getGame())
                .extracting(GameEntity::getName, gameEntity -> gameEntity.getMap().getName(), gameEntity -> gameEntity.getCars().size())
                .containsExactly("fifa", "map1", 1);
        assertThat(saved.getPositionX()).isEqualTo(1);
        assertThat(saved.getPositionY()).isEqualTo(3);
        assertThat(saved.getDirection()).isEqualTo(SOUTH);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( of(emptyList()), of(singletonList("FIAT")), of(0));

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindTwoByCarAndMapName() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements( of(singletonList("FIFA")), of(singletonList("FIAT")), of(0));

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldFindOnlyLastMovements() {
        //when
        List<MovementsHistoryEntity> result = repository.findMovements(of(singletonList("FIFA")), of(singletonList("FIAT")), of(1));

        //then
        assertThat(result).hasSize(1).extracting(MovementsHistoryEntity::getPositionX, MovementsHistoryEntity::getPositionY).contains(tuple(2, 2));
    }

    @Test
    public void shouldNotFindBecauseOfWrongGameId(){
        //when
        List<MovementsHistoryEntity> result = repository.findMovements(of(singletonList("NBA")), of(singletonList("FIAT")), of(0));

        //then
        assertThat(result).isEmpty();
    }
}
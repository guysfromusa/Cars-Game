package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.MovementsHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
public class MovementsHistoryRepositoryTest {

    @Inject
    private MovementsHistoryRepository repository;

    @Test
    public void shouldSaveSingleMovement(){
        //given
        MovementsHistory toSave = new MovementsHistory(-1L, "fiat126p", "krakow", new Point(0,0));

        //when
        MovementsHistory saved = repository.save(toSave);

        //then
        assertThat(saved.getCarName()).isEqualTo("fiat126p");
        assertThat(saved.getMapName()).isEqualTo("krakow");
        assertThat(saved.getGameId()).isEqualTo(-1L);
        assertThat(saved.getPosition()).isEqualTo(new Point(0,0));
    }

}
package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.MapEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
public class MapRepositoryTest {

    @Inject
    private MapRepository mapRepository;

    @Test
    public void shouldSaveMapToDb() {
        //given
        MapEntity map = new MapEntity("testMap");
        //when
        MapEntity savedMap = mapRepository.save(map);
        //then
        assertThat(savedMap.getName()).isEqualTo("testMap");
    }
}
package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.entities.Map;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
public class MapRepositoryTest extends BaseRepositoryTest {

    @Inject
    private MapRepository mapRepository;

    @Test
    @DatabaseSetup("/insert-map-data.xml")
    public void shouldSaveMapToDb() {
        //given
        Map map = new Map("testMap3");
        //when
        mapRepository.save(map);
        //then
        Iterable<Map> savedMaps = mapRepository.findAll();
        assertThat(savedMaps)
                .extracting(Map::getName)
                .containsOnly("testMap1", "testMap2", "testMap3");
    }
}
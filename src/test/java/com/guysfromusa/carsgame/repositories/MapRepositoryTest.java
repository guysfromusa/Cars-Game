package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
        MapEntity map = new MapEntity("testMap");
        //when
        MapEntity savedMap = mapRepository.save(map);
        //then
        Iterable<MapEntity> savedMaps = mapRepository.findAll();
        assertThat(savedMaps)
                .extracting(MapEntity::getName)
                .containsOnly("testMap1", "testMap2", "testMap3");
    }
}
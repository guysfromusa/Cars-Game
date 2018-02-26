package com.guysfromusa.carsgame.services;

import com.google.common.collect.Lists;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@RunWith(MockitoJUnitRunner.class)
public class MapServiceTest {

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private MapService mapService;

    @Test
    public void shouldFindAllMaps() {
        //given
        List<MapEntity> collection = Lists.newArrayList(new MapEntity("testMap"));
        when(mapRepository.findAll()).thenReturn(collection);
        //when
        Iterable<MapEntity> result = mapService.findAll();
        //then
        assertThat(result)
                .hasSize(1)
                .extracting(MapEntity::getName)
                .containsOnly("testMap");
    }
}
package com.guysfromusa.carsgame.services;

import com.google.common.collect.Lists;
import com.guysfromusa.carsgame.entities.Map;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        Iterable<Map> collection = Lists.newArrayList(new Map("testMap"));
        when(mapRepository.findAll()).thenReturn(collection);
        //when
        Iterable<Map> result = mapService.findAll();
        //then
        assertThat(result)
                .hasSize(1)
                .extracting(Map::getName)
                .containsOnly("testMap");
    }
}
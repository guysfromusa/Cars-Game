package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Map;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Robert Mycek, 2018-02-28
 */
public class MapConverterTest {

    private MapConverter mapConverter = new MapConverter();

    @Test
    public void fromEntity() {
        //given
        MapEntity mapEntity = new MapEntity("name", "content");
        //when
        Map map = mapConverter.convert(mapEntity);
        //then
        assertThat(map.getName()).isEqualTo("name");
        assertThat(map.getContent()).isEqualTo("content");
    }
}
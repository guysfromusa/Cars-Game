package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Map;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Robert Mycek, 2018-02-28
 */
public class MapConverterTest {

    private MapConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new MapConverter();
    }

    @Test
    public void toEntity() {
        MapEntity entity = converter.toEntity(new Map("name", "content"));
        assertThat(entity.getName()).isEqualTo("name");
        assertThat(entity.getContent()).isEqualTo("content");
    }

    @Test
    public void fromEntity() {
        Map map = converter.fromEntity(new MapEntity("name", "content"));
        assertThat(map.getName()).isEqualTo("name");
        assertThat(map.getContent()).isEqualTo("content");
    }
}
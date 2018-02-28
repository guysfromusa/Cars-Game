package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Map;
import org.springframework.stereotype.Component;

/**
 * Created by Robert Mycek, 2018-02-26
 */
@Component
public class MapConverter {
    public Map fromEntity(MapEntity mapEntity) {
        return new Map(mapEntity.getName(), mapEntity.getContent());
    }
}

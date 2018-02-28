package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Map;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Created by Robert Mycek, 2018-02-26
 */
@NoArgsConstructor(access = PRIVATE)
public class MapConverter {

    public static Map fromEntity(MapEntity mapEntity) {
        return new Map(mapEntity.getName(), mapEntity.getContent());
    }
}

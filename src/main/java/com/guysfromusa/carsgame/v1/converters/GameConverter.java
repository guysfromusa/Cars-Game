package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Game;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@Component
public class GameConverter implements Converter<GameEntity, Game> {

    @Override
    public Game convert(GameEntity gameEntity) {
        String mapName = ofNullable(gameEntity)
                .map(GameEntity::getMap)
                .map(MapEntity::getName)
                .orElseThrow(() -> new IllegalArgumentException("Map is required"));

        return new Game(gameEntity.getName(), mapName);
    }

}

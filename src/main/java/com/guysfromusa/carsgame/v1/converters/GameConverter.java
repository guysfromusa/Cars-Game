package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Game;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@NoArgsConstructor(access = PRIVATE)
public class GameConverter {

    public static Game toGame(GameEntity gameEntity) {
        String mapName = Optional.ofNullable(gameEntity.getMap())
                .map(MapEntity::getName)
                .orElseThrow(() -> new IllegalArgumentException("Map is required"));

        return new Game(gameEntity.getName(), mapName);
    }
}

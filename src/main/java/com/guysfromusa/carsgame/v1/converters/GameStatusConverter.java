package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.model.GameStatusDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
//TODO use ModelMapper and rewrite converters
@Component
public class GameStatusConverter implements Converter<GameEntity, GameStatusDto> {

    @Override
    public GameStatusDto convert(GameEntity source) {
        return new GameStatusDto(source.getGameStatus());
    }

}

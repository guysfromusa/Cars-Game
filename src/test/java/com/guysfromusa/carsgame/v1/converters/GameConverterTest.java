package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Tomasz Bradlo, 28.02.18
 */
public class GameConverterTest {

    @Test
    public void shouldConvertGameEntityToDto(){
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setName("map1");

        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");
        gameEntity.setMap(mapEntity);

        //when
        Game game = GameConverter.toGame(gameEntity);

        //then
        assertEquals("game1", game.getName());
        assertEquals("map1", game.getMapName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfEntityNotComplete(){
        //given
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");

        //when
        Game game = GameConverter.toGame(gameEntity);
    }
}
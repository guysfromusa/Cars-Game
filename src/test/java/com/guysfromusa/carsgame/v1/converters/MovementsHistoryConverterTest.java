package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.junit.Test;
import org.springframework.data.geo.Point;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MovementsHistoryConverterTest {

    @Test
    public void shouldConvertSingleEntity(){
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("fiat126p");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("sampleGame");
        MovementsHistoryEntity movementsHistoryEntity = new MovementsHistoryEntity(gameEntity, carEntity, 0, 0);

        //when
        MovementHistory translated = MovementsHistoryConverter.toMovementHisotry(movementsHistoryEntity);

        //then
        assertThat(translated.getCarName()).isEqualTo("fiat126p");
        assertThat(translated.getGameName()).isEqualTo("sampleGame");

    }
}
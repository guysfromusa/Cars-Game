package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static com.guysfromusa.carsgame.model.Direction.SOUTH;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class MovementsHistoryConverterTest {

    private MovementsHistoryConverter movementsHistoryConverter = new MovementsHistoryConverter();

    @Test
    public void shouldConvertSingleEntity(){
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("fiat126p");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("sampleGame");
        MovementsHistoryEntity entity = new MovementsHistoryEntity();
        entity.setCar(carEntity);
        entity.setGame(gameEntity);
        entity.setPositionX(2);
        entity.setPositionY(5);
        entity.setDirection(SOUTH);

        //when
        MovementHistory gotDto = movementsHistoryConverter.convert(entity);

        //then
        assertThat(gotDto.getCarName()).isEqualTo("fiat126p");
        assertThat(gotDto.getGameName()).isEqualTo("sampleGame");
        Point point = gotDto.getPosition();
        assertThat(point.getX()).isEqualTo(2);
        assertThat(point.getY()).isEqualTo(5);
        assertThat(gotDto.getDirection()).isEqualTo(SOUTH);
    }
}
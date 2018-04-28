package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import org.junit.Test;

import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public class CarConverterTest {

    private final CarConverter carConverter = new CarConverter();

    @Test
    public void shouldConvertAllFields() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("car1");
        carEntity.setDirection(NORTH);

        //when
        Car gotCar = carConverter.convert(carEntity);

        //then
        assertThat(gotCar.getName()).isEqualTo("car1");
        assertThat(gotCar.getDirection()).isEqualTo(NORTH);
    }

}
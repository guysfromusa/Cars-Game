package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import org.junit.Test;

import java.util.List;

import static com.guysfromusa.carsgame.model.Direction.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;


/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public class CarConverterTest {

    @Test
    public void shouldConvertAllFields() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("car1");
        carEntity.setDirection(NORTH);

        //when
        Car gotCar = CarConverter.toCar(carEntity);

        //then
        assertThat(gotCar.getName()).isEqualTo("car1");
        assertThat(gotCar.getDirection()).isEqualTo(NORTH);
    }

    @Test
    public void shouldConvertListOfCars() {
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("car1");
        carEntity.setDirection(EAST);

        CarEntity carEntity2 = new CarEntity();
        carEntity2.setName("car2");
        carEntity2.setDirection(SOUTH);

        //when
        List<Car> gotCars = CarConverter.toCars(asList(carEntity, carEntity2));

        //then
        assertThat(gotCars)
                .extracting(Car::getName, Car::getDirection)
                .containsExactly(
                        tuple("car1", EAST),
                        tuple("car2", SOUTH));
    }

}
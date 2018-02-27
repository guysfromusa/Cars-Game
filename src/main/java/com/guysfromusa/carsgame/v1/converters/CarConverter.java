package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.google.common.collect.Lists.transform;
import static lombok.AccessLevel.PRIVATE;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@NoArgsConstructor(access = PRIVATE)
public class CarConverter {

    public static Car toCar(CarEntity carEntity){
        Car car = new Car();
        car.setName(carEntity.getName());
        car.setDirection(carEntity.getDirection());
        return car;
    }

    public static List<Car> toCars(List<CarEntity> carEntities){
        return transform(carEntities, CarConverter::toCar);
    }
}

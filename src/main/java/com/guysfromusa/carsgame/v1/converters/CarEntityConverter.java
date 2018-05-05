package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Component
public class CarEntityConverter implements Converter<CarEntity, Car> {

    @Override
    public Car convert(CarEntity carEntity) {
        Car car = Car.builder()
                .name(carEntity.getName())
                .type(carEntity.getCarType())
                .direction(carEntity.getDirection()).build();

        ofNullable(carEntity.getGame())
                .map(GameEntity::getName)
                .ifPresent(car::setGame);

        Integer positionX = carEntity.getPositionX();
        Integer positionY = carEntity.getPositionY();
        Point point = new Point(positionX, positionY);

        car.setPosition(point);

        return car;
    }

}

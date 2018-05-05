package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Component
public class CarDtoConverter implements Converter<CarDto, Car> {

    @Override
    public Car convert(CarDto carDto) {
        return Car.builder()
                .name(carDto.getName())
                .type(carDto.getType())
                .game(carDto.getGame())
                .position(carDto.getPosition())
                .direction(carDto.getDirection()).build();
    }

}

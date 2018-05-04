package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.v1.model.Car;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarState {

    private final Car car = new Car();
    private final List<Movement> movements = new ArrayList<>();

    public static CarState newCarState(){
        return new CarState();
    }

}

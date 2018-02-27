package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.TurnSide;

import java.util.List;

public interface CarService {

    long deleteCarByName(String name);

    Long addCar(CarType carType, String name);

    List<CarEntity> loadAllCars();

    List<CarEntity> findCars(String game);

    void turnCar(String game, String carName, TurnSide turnSide);

}

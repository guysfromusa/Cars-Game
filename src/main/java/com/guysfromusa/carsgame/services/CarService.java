package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;

public interface CarService {

    long deleteCarByName(String name);

    CarEntity addCar(CarType carType, String name);

    Iterable<CarEntity> loadAllCars();

}

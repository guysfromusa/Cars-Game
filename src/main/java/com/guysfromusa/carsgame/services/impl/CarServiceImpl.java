package com.guysfromusa.carsgame.services.impl;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.services.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Inject
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public long deleteCarByName(String name) {
        return carRepository.deleteByName(name);
    }

    @Override
    public CarEntity addCar(CarType carType, String name) {
        CarEntity car = new CarEntity();
        car.setCarType(carType);
        car.setName(name);
        return carRepository.save(car);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<CarEntity> loadAllCars() {
        return carRepository.findAll();
    }
}

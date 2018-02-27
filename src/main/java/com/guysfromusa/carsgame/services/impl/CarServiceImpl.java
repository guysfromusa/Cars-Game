package com.guysfromusa.carsgame.services.impl;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.converters.CarConverter;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.singletonList;

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
    public Long addCar(CarType carType, String name) {
        CarEntity car = new CarEntity();
        car.setCarType(carType);
        car.setName(name);

        CarEntity newCar = carRepository.save(car);
        return newCar.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarEntity> loadAllCars() {
        Iterable<CarEntity> allCarEntities = carRepository.findAll();
        return IteratorUtils.toList(allCarEntities.iterator());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarEntity> findCars(String game) {
        return singletonList(new CarEntity(){{ //FIXME implement me
            this.setName("car1");
        }});
    }

    @Transactional
    @Override
    public void turnCar(String game, String carName, TurnSide turnSide) {
        //TODO implement me
        //uncomment me once adding cars will be reedy
        /*
        //FIXME handle car not found

        Function<Direction, Direction> turnF = turnSide == LEFT ? Direction::turnLeft : Direction::turnRight;

        Direction newDirection = turnF.apply(car.getDirection());
        car.setDirection(newDirection);
        */

        //update movements
    }
}

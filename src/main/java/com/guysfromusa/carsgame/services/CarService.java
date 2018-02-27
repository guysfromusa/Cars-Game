package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.services.CarService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    @Inject
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Long deleteCarByName(String name) {
        return carRepository.deleteByName(name);
    }

    public Long addCar(CarType carType, String name) {
        CarEntity car = new CarEntity();
        car.setCarType(carType);
        car.setName(name);

        CarEntity newCar = carRepository.save(car);
        Long newId = Optional.ofNullable(newCar).map(CarEntity::getId).orElse(null);
        return newId;
    }

    @Transactional(readOnly = true)
    public List<CarEntity> loadAllCars() {
        Iterable<CarEntity> allCarEntities = carRepository.findAll();
        return IteratorUtils.toList(allCarEntities.iterator());
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findCars(String game) {
        return singletonList(new CarEntity(){{ //FIXME implement me
            this.setName("car1");
        }});
    }

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

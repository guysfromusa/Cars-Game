package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;

import static com.guysfromusa.carsgame.model.TurnSide.LEFT;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    @Inject
    public CarService(CarRepository carRepository){
        this.carRepository = notNull(carRepository);
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findCars(String game) {
        return carRepository.findByGame(game);
    }

    @Transactional
    public void turnCar(String game, String carName, TurnSide turnSide) {
        CarEntity car = carRepository.findByGameAndName(game, carName);
        //FIXME handle car not found

        Function<Direction, Direction> turnF = turnSide == LEFT ? Direction::turnLeft : Direction::turnRight;

        Direction newDirection = turnF.apply(car.getDirection());
        car.setDirection(newDirection);

        //update movements
    }
}

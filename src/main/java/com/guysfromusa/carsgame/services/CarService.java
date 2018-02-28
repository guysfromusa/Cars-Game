package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validators.CarGameAdditionValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Function;

import static com.guysfromusa.carsgame.model.TurnSide.LEFT;
import static org.apache.commons.collections4.IteratorUtils.toList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    private final GameRepository gameRepository;

    @Inject
    public CarService(CarRepository carRepository,
                      GameRepository gameRepository,
                      CarGameAdditionValidator carGameAdditionValidator){
        this.carRepository = notNull(carRepository);
        this.gameRepository = notNull(gameRepository);
    }


    public Long deleteCarByName(String name) {
        return carRepository.deleteByName(name);
    }

    public CarEntity addCar(CarType carType, String name) {
        CarEntity car = new CarEntity();
        car.setCarType(carType);
        car.setName(name);

        return carRepository.save(car);
    }

    @Transactional(readOnly = true)
    public List<CarEntity> loadAllCars() {
        Iterable<CarEntity> allCarEntities = carRepository.findAll();
        return toList(allCarEntities.iterator());
    }


    @Transactional(readOnly = true)
    public List<CarEntity> findCars(String game) {
        return carRepository.findByGame(game);
    }

    public void turnCar(String game, String carName, TurnSide turnSide) {
        CarEntity car = carRepository.findByGameAndName(game, carName);
        //FIXME handle car not found

        Function<Direction, Direction> turnF = turnSide == LEFT ? Direction::turnLeft : Direction::turnRight;

        Direction newDirection = turnF.apply(car.getDirection());
        car.setDirection(newDirection);

        //update movements
    }

    public CarEntity addCarToGame(String carName, String gameName, Point startingPoint){
        CarEntity car = carRepository.findByName(carName);

        //TODO use validator

        Integer positionX = startingPoint.getX();
        Integer positionY = startingPoint.getY();

        car.setPositionX(positionX);
        car.setPositionY(positionY);

        GameEntity game = gameRepository.findByName(car.getName());
        car.setGame(game);

        return carRepository.save(car);
    }

}

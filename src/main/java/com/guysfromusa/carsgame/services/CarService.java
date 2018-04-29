package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.CarGameAdditionValidator;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final MovementsHistoryRepository movementsHistoryRepository;

    @Autowired
    private CarGameAdditionValidator carGameAdditionValidator;

    @Inject
    public CarService(CarRepository carRepository, MovementsHistoryRepository movementsHistoryRepository,
                      GameRepository gameRepository, CarGameAdditionValidator carGameAdditionValidator){
        this.carRepository = notNull(carRepository);
        this.movementsHistoryRepository = notNull(movementsHistoryRepository);
        this.gameRepository = notNull(gameRepository);
        this.carGameAdditionValidator = carGameAdditionValidator;
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

    public MovementsHistoryEntity turnCar(String gameName, String carName, TurnSide turnSide) {
        GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new EntityNotFoundException("Game '" + gameName + "' not found"));

        CarEntity carEntity = carRepository.findByGameAndName(gameName, carName)
                .orElseThrow(() -> new EntityNotFoundException("Car '" + carName + "' not found"));

        Function<Direction, Direction> turnF = turnSide == LEFT ? Direction::turnLeft : Direction::turnRight;

        Direction newDirection = turnF.apply(carEntity.getDirection());
        carEntity.setDirection(newDirection);

        MovementsHistoryEntity movementEntity = new MovementsHistoryEntity();
        movementEntity.setCar(carEntity);
        movementEntity.setGame(gameEntity);
        movementEntity.setPositionX(carEntity.getPositionX());
        movementEntity.setPositionY(carEntity.getPositionY());
        movementEntity.setDirection(carEntity.getDirection());
        return movementsHistoryRepository.save(movementEntity);
    }

    public CarEntity addCarToGame(String carName, String gameName, Point startingPoint){
        CarEntity car = carRepository.findByName(carName)
                .orElseThrow(() -> new EntityNotFoundException("Car '" + carName + "' not found"));

        GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new EntityNotFoundException("Game '" + gameName + "' not found"));

        CarGameAdditionValidationSubject validationSubject =
                new CarGameAdditionValidationSubject(car, gameEntity, startingPoint);
        carGameAdditionValidator.validateCarBeforeAddition(validationSubject);

        Integer positionX = startingPoint.getX();
        Integer positionY = startingPoint.getY();

        car.setPositionX(positionX);
        car.setPositionY(positionY);

        car.setDirection(Direction.NORTH);
        car.setGame(gameEntity);

        return carRepository.save(car);
    }

}

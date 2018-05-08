package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.BusinessValidator;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.collections4.IteratorUtils.toList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Slf4j
@Service
@Transactional
public class CarService {

    private final CarRepository carRepository;

    private final GameRepository gameRepository;

    private final List<BusinessValidator<CarGameAdditionValidationSubject>> validators;

    @Inject
    public CarService(CarRepository carRepository,
                      GameRepository gameRepository,
                      List<BusinessValidator<CarGameAdditionValidationSubject>> validators){
        this.carRepository = notNull(carRepository);
        this.gameRepository = notNull(gameRepository);
        this.validators = notNull(validators);
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


    public CarEntity addCarToGame(String carName, GameState gameState, Point startingPoint) {
        CarEntity car = carRepository.findByName(carName)
                .orElseThrow(() -> new EntityNotFoundException("Car '" + carName + "' not found"));

        log.debug("Found car: {}", car);

        GameEntity gameEntity = gameRepository.findByName(gameState.getGameName())
                .orElseThrow(() -> new EntityNotFoundException("Game '" + gameState + "' not found"));

        log.debug("Found game: {}", gameEntity);

        CarGameAdditionValidationSubject validationSubject =
                CarGameAdditionValidationSubject.builder()
                        .carEntity(car)
                        .gameEntity(gameEntity)
                        .startingPoint(startingPoint)
                        .gameState(gameState)
                        .build();

        validators.forEach(validator -> validator.validate(validationSubject));
        log.debug("Validation successful");

        Integer positionX = startingPoint.getX();
        Integer positionY = startingPoint.getY();

        car.setPositionX(positionX);
        car.setPositionY(positionY);

        car.setDirection(Direction.NORTH);
        car.setGame(gameEntity);
        return carRepository.save(car);
    }

    public void crashAndRemoveFromGame(String gameName, CarDto car) {
        CarEntity carEntity = carRepository.findByGameAndName(gameName, car.getName())
                .orElseThrow(() -> new EntityNotFoundException("Car '" + car.getName() + "' not found"));

        carEntity.setCrashed(true);
        removeCarFromGame(carEntity);
    }

    public void removeAllCarsFromGame(String gameName) {
        carRepository.findByGame(gameName).forEach(this::removeCarFromGame);
    }

    private void removeCarFromGame(CarEntity carEntity){
        carEntity.setGame(null);
        carEntity.setPositionX(null);
        carEntity.setPositionY(null);
    }

    public CarEntity repairCar(String name) {
        CarEntity car = carRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Car with name " + name + " does not exist"));
        car.setCrashed(false);
        return car;
    }
}

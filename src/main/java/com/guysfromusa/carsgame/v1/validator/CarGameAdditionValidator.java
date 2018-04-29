package com.guysfromusa.carsgame.v1.validator;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CarGameAdditionValidator{

    public static final String CAR_CRASHED_MESSAGE = "Car is aready crashed";
    public static final String CAR_EXISTS_IN_GAME_MESSAGE = "Car is aready added to game";
    public static final String WRONG_STARTING_POINT_MESSAGE = "Starting point is invalid";

    private MapService mapService;

    public CarGameAdditionValidator(@Autowired MapService mapService) {
        this.mapService = mapService;
    }

    public void validateCarBeforeAddition(CarGameAdditionValidationSubject validationSubject) {
        isCarNotAlreadyInGame()
            .andThen(isCarNotCrashed())
            .andThen(areStartingCoordinatesValid())
            .accept(validationSubject);
    }


    private Consumer<CarGameAdditionValidationSubject> isCarNotAlreadyInGame(){
        return validationSubject -> {
            Set<CarEntity> carsInGame = validationSubject.getGameEntity().getCars();
            List<String> gamesCarNames = carsInGame.stream().map(CarEntity::getName).collect(Collectors.toList());

            String carName = validationSubject.getCarEntity().getName();

            if(gamesCarNames.contains(carName)){
                throw new IllegalArgumentException(CAR_EXISTS_IN_GAME_MESSAGE);
            }
        };
    }

    private Consumer<CarGameAdditionValidationSubject> isCarNotCrashed(){
        return validationSubject -> {
            boolean crashed = validationSubject.getCarEntity().isCrashed();
            if(crashed){
                throw new IllegalArgumentException(CAR_CRASHED_MESSAGE);
            }
        };
    }

    private Consumer<CarGameAdditionValidationSubject> areStartingCoordinatesValid(){
        return validationSubject -> {
            Point startingPoint = Optional.ofNullable(validationSubject.getStartingPoint())
                    .orElseThrow(() -> new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE));

            MapEntity gameMap = validationSubject.getGameEntity().getMap();
            String gameMapContent = gameMap.getContent();

            if(!mapService.isPositionValidOnGameMap(gameMapContent, startingPoint)){
                throw new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE);
            }
        };
    }
}

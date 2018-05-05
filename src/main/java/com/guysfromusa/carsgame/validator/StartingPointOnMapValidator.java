package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static java.util.Optional.ofNullable;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Slf4j
@Component
public class StartingPointOnMapValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String WRONG_STARTING_POINT_MESSAGE = "Starting point is invalid";

    private final MapService mapService;

    @Inject
    public StartingPointOnMapValidator(MapService mapService) {
        this.mapService = Validate.notNull(mapService);
    }

    @Override
    public void validate(CarGameAdditionValidationSubject subject) {
        Point startingPoint = ofNullable(subject.getStartingPoint())
                .orElseThrow(() -> new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE));

        MapEntity gameMap = subject.getGameEntity().getMap();
        String gameMapContent = gameMap.getContent();

        if(!mapService.isFieldReachableOnGameMap(gameMapContent, startingPoint)){
            log.debug("Starting point is invalid");
            throw new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE);
        }
    }

}

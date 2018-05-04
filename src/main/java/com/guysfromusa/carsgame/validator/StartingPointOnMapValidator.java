package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static java.util.Optional.ofNullable;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class StartingPointOnMapValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String WRONG_STARTING_POINT_MESSAGE = "Starting point is invalid";

    private final MapService mapService;

    @Inject
    public StartingPointOnMapValidator(MapService mapService) {
        this.mapService = Validate.notNull(mapService);
    }

    @Override
    public void validate(CarGameAdditionValidationSubject validationSubject) {
        Point startingPoint = ofNullable(validationSubject.getStartingPoint())
                .orElseThrow(() -> new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE));

        MapEntity gameMap = validationSubject.getGameEntity().getMap();
        String gameMapContent = gameMap.getContent();

        if(!mapService.isPositionValidOnGameMap(gameMapContent, startingPoint)){
            throw new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE);
        }
    }

}

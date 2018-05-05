package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.GameMapUtils.getMapMatrixFromContent;
import static java.util.Optional.ofNullable;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class StartingPointOnMapValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String WRONG_STARTING_POINT_MESSAGE = "Starting point is invalid";

    @Override
    public void validate(CarGameAdditionValidationSubject validationSubject) {
        Point startingPoint = ofNullable(validationSubject.getStartingPoint())
                .orElseThrow(() -> new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE));

        MapEntity gameMap = validationSubject.getGameEntity().getMap();
        String gameMapContent = gameMap.getContent();
        Integer[][] mapMatrixFromContent = getMapMatrixFromContent(gameMapContent);
        if(!GameMapUtils.isPointOnRoad(mapMatrixFromContent, startingPoint)){
            throw new IllegalArgumentException(WRONG_STARTING_POINT_MESSAGE);
        }
    }

}

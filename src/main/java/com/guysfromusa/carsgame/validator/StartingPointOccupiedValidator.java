package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.guysfromusa.carsgame.utils.StreamUtils.convert;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Component
public class StartingPointOccupiedValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String STARTING_POINT_OCCUPIED_MESSAGE = "Starting point is already occupied by another car";

    @Override
    public void validate(CarGameAdditionValidationSubject validationSubject) {
        Point startingPoint = validationSubject.getStartingPoint();
        List<Tuple2<Integer, Integer>> occupiedPoints = convert(validationSubject.getGameEntity().getCars(), carEntity -> Tuple.of(carEntity.getPositionX(), carEntity.getPositionY()));
        Tuple2<Integer, Integer> startingPointTuple2 = Tuple.of(startingPoint.getX(), startingPoint.getY());
        if (occupiedPoints.stream().anyMatch(startingPointTuple2::equals)) {
            throw new IllegalArgumentException(STARTING_POINT_OCCUPIED_MESSAGE);
        }
    }

}

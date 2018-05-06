package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class StartingPointOnMapValidatorTest {


    private StartingPointOnMapValidator startingPointOnMapValidator = new StartingPointOnMapValidator();

    @Test
    public void shouldPassValidationWhenStartingPointOnTheMap() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setContent("0,1,1,0\n0,0,1,0");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setMap(mapEntity);

        Point startingPoint = new Point(2, 0);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .gameEntity(gameEntity)
                .startingPoint(startingPoint)
                .build();
        //when

        startingPointOnMapValidator.validate(subject);

    }

    @Test
    public void shouldThrowExceptionWhenStartingPointNotOnTheMap() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setContent("0,1,1,0\n0,0,1,0");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setMap(mapEntity);

        Point startingPoint = new Point(0, 0);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .gameEntity(gameEntity)
                .startingPoint(startingPoint)
                .build();

        //when then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> startingPointOnMapValidator.validate(subject))
                .withMessage(StartingPointOnMapValidator.WRONG_STARTING_POINT_MESSAGE);

    }
}
package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(MockitoJUnitRunner.class)
public class StartingPointOnMapValidatorTest {

    @Mock
    private MapService mapService;

    @InjectMocks
    private StartingPointOnMapValidator startingPointOnMapValidator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationWhenStartingPointOnTheMap() {
        //given
        MapEntity mapEntity = new MapEntity();
        mapEntity.setContent("0,1,1,0\n0,0,1,0");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setMap(mapEntity);

        Point startingPoint = new Point(2, 0);

        Mockito.when(mapService.isPositionValidOnGameMap(Matchers.anyString(), Mockito.any())).thenReturn(true);

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
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(StartingPointOnMapValidator.WRONG_STARTING_POINT_MESSAGE);

        MapEntity mapEntity = new MapEntity();
        mapEntity.setContent("0,1,1,0\n0,0,1,0");
        GameEntity gameEntity = new GameEntity();
        gameEntity.setMap(mapEntity);

        Point startingPoint = new Point(0, 0);

        Mockito.when(mapService.isPositionValidOnGameMap(Matchers.anyString(), Mockito.any())).thenReturn(false);

        CarGameAdditionValidationSubject subject = CarGameAdditionValidationSubject.builder()
                .gameEntity(gameEntity)
                .startingPoint(startingPoint)
                .build();
        //when

        startingPointOnMapValidator.validate(subject);

    }
}
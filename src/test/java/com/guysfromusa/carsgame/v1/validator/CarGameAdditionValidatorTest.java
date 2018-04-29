package com.guysfromusa.carsgame.v1.validator;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.exceptions.ValidationException;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarGameAdditionValidatorTest {

    @Mock
    private MapService mapService;

    @InjectMocks
    private CarGameAdditionValidator carGameAdditionValidator;

    @Test
    public void shouldValidateCarAdditionForGivenGame(){
        //given
        String gameMapContent = "1,1\n1,0\n1,0\n1,1";

        CarEntity carEntity = mock(CarEntity.class);
        when(carEntity.isCrashed()).thenReturn(false);

        boolean isCarInGame = false;
        GameEntity gameEntity = createGameEntity(gameMapContent, isCarInGame);

        Point startingPoint = new Point(2, 1);

        when(mapService.isPositionValidOnGameMap(gameMapContent, startingPoint)).thenReturn(true);

        CarGameAdditionValidationSubject carGameAdditionValidationSubject =
                new CarGameAdditionValidationSubject(carEntity, gameEntity, startingPoint);

        //when
        carGameAdditionValidator.validateCarBeforeAddition(carGameAdditionValidationSubject);

        verify(mapService).isPositionValidOnGameMap(eq(gameMapContent), eq(startingPoint));
    }

    @Test(expected = ValidationException.class)
    public void shouldFailureCarAdditionForCrashedCar(){
        //given
        String gameMapContent = "1,1\n1,0\n1,0\n1,1";

        CarEntity carEntity = mock(CarEntity.class);
        when(carEntity.isCrashed()).thenReturn(true);

        boolean isCarInGame = false;
        GameEntity gameEntity = createGameEntity(gameMapContent, isCarInGame);

        Point startingPoint = new Point(2, 1);

        when(mapService.isPositionValidOnGameMap(gameMapContent, startingPoint)).thenReturn(true);

        CarGameAdditionValidationSubject carGameAdditionValidationSubject =
                new CarGameAdditionValidationSubject(carEntity, gameEntity, startingPoint);

        //when
        carGameAdditionValidator.validateCarBeforeAddition(carGameAdditionValidationSubject);

    }

    @Test(expected = ValidationException.class)
    public void shouldFailureCarAdditionWhenCarAlreadyAssigned(){
        //given
        String gameMapContent = "1,1\n1,0\n1,0\n1,1";

        CarEntity carEntity = mock(CarEntity.class);
        when(carEntity.getName()).thenReturn("Car1");
        when(carEntity.isCrashed()).thenReturn(false);

        boolean isCarInGame = true;
        GameEntity gameEntity = createGameEntity(gameMapContent, isCarInGame);

        Point startingPoint = new Point(2, 1);

        when(mapService.isPositionValidOnGameMap(gameMapContent, startingPoint)).thenReturn(true);

        CarGameAdditionValidationSubject carGameAdditionValidationSubject =
                new CarGameAdditionValidationSubject(carEntity, gameEntity, startingPoint);

        //when
        carGameAdditionValidator.validateCarBeforeAddition(carGameAdditionValidationSubject);

    }

    private GameEntity createGameEntity(String gameMapContent, boolean isCarInGame) {


        MapEntity mapEntity = mock(MapEntity.class);
        when(mapEntity.getContent()).thenReturn(gameMapContent);

        GameEntity gameEntity = mock(GameEntity.class);

        when(gameEntity.getMap()).thenReturn(mapEntity);

        if(isCarInGame){
            CarEntity carEntity = mock(CarEntity.class);
            when(carEntity.getName()).thenReturn("Car1");
            List<CarEntity> carEntities = asList(carEntity);
            when(gameEntity.getCars()).thenReturn(new HashSet<>(carEntities));
        }
        return gameEntity;
    }
}
package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static com.guysfromusa.carsgame.v1.model.Movement.Operation.FORWARD;

/**
 * Created by Konrad Rys, 06.05.2018
 */
public abstract class TestGameAware implements CarApiAware, GameApiAware, MapApiAware{

    protected IllegalArgumentException crashCarIntoWall(TestRestTemplate template, String game, String carName, String mapName){
        addNewCar(template, carName, CarType.RACER);
        startNewGame(template, game, mapName);

        Point startingPoint = new Point(0,0);
        addCarToGame(template, carName, game, startingPoint);

        Movement movement = new Movement(FORWARD, 2);

        //when
        return doCarMoveWithExpcetedError(template, game, carName, movement);
    }
}

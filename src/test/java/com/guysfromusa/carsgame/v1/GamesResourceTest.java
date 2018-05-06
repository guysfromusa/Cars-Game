package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class GamesResourceTest implements CarApiAware, MapApiAware, GameApiAware {

    @Inject
    private TestRestTemplate template;

    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldStartTheGame(){
        //given
        addNewMap(template, new Map("mapToAssign", "1"));

        //when
        Game game = startNewGame(template, "gameToStart", "mapToAssign");

        //then
        assertThat(game)
                .extracting(Game::getName,Game::getMapName)
                .containsExactly("gameToStart", "mapToAssign");
    }

    @Test
    @Sql(value = {"/sql/clean.sql","/sql/gameResource_insertGame.sql"})
    public void shouldReturnStatusOfTheGame() {
        //when
        GameStatusDto entity = template.getForObject("/v1/games/game2", GameStatusDto.class);
        //then
        assertThat(entity)
                .extracting(GameStatusDto::getStatus)
                .containsExactly(GameStatus.RUNNING);
    }

    @Test
    public void whenTurnRightMoveForward_shouldCarBeHeadingEast(){
        //given
        String carName = "car4";
        String gameName = "game4";
        String mapName = "map2";

        Point startingPoint = new Point(0,0);

        addNewCar(template, carName, CarType.MONSTER);
        startNewGame(template, gameName, mapName);
        addCarToGame(template, carName, gameName, startingPoint);

        //when
        Movement movement = Movement.newMovement(Movement.Operation.RIGHT);
        doCarMove(template, gameName, carName, movement);

        Movement movement2 = Movement.newMovement(Movement.Operation.FORWARD, 2);
        List<Car> cars = doCarMove(template, gameName, carName, movement2);

        //then
        assertThat(cars).first()
                .extracting(Car::getDirection, Car::getName, Car::getGame, Car::isCrashed)
                .containsExactly(Direction.EAST, "car4", "game4", false);

        assertThat(cars).extracting(Car::getPosition).extracting(Point::getX, Point::getY)
                .containsExactly(tuple(2, 0));
    }


    @Test
    public void whenHeadingNorthCommand_shouldCarBeCrashedIntoWall(){
        //given
        String carName = "car5";
        String gameName = "game5";
        String mapName = "map2";

        addNewCar(template, carName, CarType.RACER);
        startNewGame(template, gameName, mapName);

        Point startingPoint = new Point(0,0);
        addCarToGame(template, carName, gameName, startingPoint);

        Movement movement = Movement.newMovement(Movement.Operation.FORWARD, 2);

        //when
        IllegalArgumentException illegalArgumentException = doCarMoveWithExpcetedError(template, gameName, carName, movement);

        //then
        ResponseEntity<ApiError> apiErrorResponseEntity = restExceptionHandler.handleBadRequest(illegalArgumentException);

        assertThat(apiErrorResponseEntity.getBody())
                .extracting(ApiError::getMessage)
                .containsExactly("Car was crashed into wall");
    }

    @Test
    public void whenTwoCarsOnPoint_shouldAllOfThemBeCrashed(){
        //given
        String carName = "car6";
        String gameName = "game6";
        String mapName = "map2";
        String secondCar = "car7";

        addNewCar(template, carName, CarType.MONSTER);
        addNewCar(template, secondCar, CarType.MONSTER);
        startNewGame(template, gameName, mapName);

        Point startingPoint = new Point(0,0);
        addCarToGame(template, carName, gameName, startingPoint);


        Movement turnRight = Movement.newMovement(Movement.Operation.RIGHT);
        doCarMove(template, gameName, carName, turnRight);

        Movement moveForward = Movement.newMovement(Movement.Operation.FORWARD, 1);
        doCarMove(template, gameName, carName, moveForward);

        addCarToGame(template, secondCar, gameName, startingPoint);
        doCarMove(template, gameName, secondCar, turnRight);

        //when
        IllegalArgumentException illegalArgumentException = doCarMoveWithExpcetedError(template, gameName, secondCar, moveForward);

        //then
        ResponseEntity<ApiError> apiErrorResponseEntity = restExceptionHandler.handleBadRequest(illegalArgumentException);

        assertThat(apiErrorResponseEntity.getBody())
                .extracting(ApiError::getMessage)
                .containsExactly("Car was crashed with other");
    }

}
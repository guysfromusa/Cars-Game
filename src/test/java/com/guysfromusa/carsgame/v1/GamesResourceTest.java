package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.GameStatusDto;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
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

import static com.guysfromusa.carsgame.v1.model.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.RIGHT;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class GamesResourceTest extends TestGameAware {

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
        Movement movement = new Movement(RIGHT, 1);
        doCarMove(template, gameName, carName, movement);
        Movement movement2 = new Movement(FORWARD, 1);
        List<Car> cars = doCarMove(template, gameName, carName, movement2);

        //then
        assertThat(cars).first()
                .extracting(Car::getDirection, Car::getName, Car::getGame, Car::isCrashed)
                .containsExactly(Direction.EAST, "car4", "game4", false);

        assertThat(cars).extracting(Car::getPosition).extracting(Point::getX, Point::getY)
                .containsExactly(tuple(1, 0));
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

        Movement movement = new Movement(FORWARD, 2);

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

        Movement turnRight = new Movement(RIGHT, 0);
        doCarMove(template, gameName, carName, turnRight);

        Movement moveForward = new Movement(FORWARD, 1);
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
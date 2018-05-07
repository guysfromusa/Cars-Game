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
    @Sql(value = {"/sql/clean.sql", "/sql/game_resource_insert_map.sql"})
    public void whenTurnRightMoveForward_shouldCarBeHeadingEast(){
        //given
        final String carName = "car4";
        final String gameName = "game4";
        final String mapName = "map2";
        final Point startingPoint = new Point(0,0);

        //when
        addNewCar(template, carName, CarType.MONSTER);
        startNewGame(template, gameName, mapName);

        addCarToGame(template, carName, gameName, startingPoint);
        doCarMove(template, gameName, carName, new Movement(RIGHT, 1));
        List<Car> cars = doCarMove(template, gameName, carName, new Movement(FORWARD, 1));

        //then
        assertThat(cars).first()
                .extracting(Car::getDirection, Car::getName, Car::getGame, Car::isCrashed)
                .containsExactly(Direction.EAST, "car4", "game4", false);

        assertThat(cars).extracting(Car::getPosition).extracting(Point::getX, Point::getY)
                .containsExactly(tuple(1, 0));
    }


    @Test
    @Sql(value = {"/sql/clean.sql", "/sql/game_resource_insert_map.sql"})
    public void whenHeadingNorthCommand_shouldCarBeCrashedIntoWall(){
        //given
        final String carName = "car5";
        final String gameName = "game5";
        final String mapName = "map2";
        final Point startingPoint = new Point(0,0);

        //when
        addNewCar(template, carName, CarType.RACER);
        startNewGame(template, gameName, mapName);

        addCarToGame(template, carName, gameName, startingPoint);
        IllegalArgumentException illegalArgumentException = doCarMoveWithExpcetedError(template, gameName, carName, new Movement(FORWARD, 2));

        //then
        ResponseEntity<ApiError> apiErrorResponseEntity = restExceptionHandler.handleBadRequest(illegalArgumentException);

        assertThat(apiErrorResponseEntity.getBody())
                .extracting(ApiError::getMessage)
                .containsExactly("Car was crashed into wall");
    }

    @Test
    @Sql(value = {"/sql/clean.sql", "/sql/game_resource_insert_map.sql"})
    public void whenTwoCarsOnPoint_shouldAllOfThemBeCrashed(){
        //given
        final String carName = "car6";
        final String gameName = "game6";
        final String mapName = "map2";
        final String secondCar = "car7";
        final Point startingPoint = new Point(0,0);

        //when
        addNewCar(template, carName, CarType.MONSTER);
        addNewCar(template, secondCar, CarType.MONSTER);
        startNewGame(template, gameName, mapName);

        addCarToGame(template, carName, gameName, startingPoint);

        doCarMove(template, gameName, carName, new Movement(RIGHT, 0));
        doCarMove(template, gameName, carName, new Movement(FORWARD, 1));

        addCarToGame(template, secondCar, gameName, startingPoint);
        doCarMove(template, gameName, secondCar, new Movement(RIGHT, 0));

        IllegalArgumentException illegalArgumentException = doCarMoveWithExpcetedError(template, gameName, secondCar, new Movement(FORWARD, 1));

        //then
        ResponseEntity<ApiError> apiErrorResponseEntity = restExceptionHandler.handleBadRequest(illegalArgumentException);

        assertThat(apiErrorResponseEntity.getBody())
                .extracting(ApiError::getMessage)
                .containsExactly("Car was crashed with other");
    }

    @Test
    @Sql(value = {"/sql/clean.sql", "/sql/game_resource_insert_map.sql"})
    public void whenMonsterAndRacingCarOnOnePoint_shouldMonsterBeAbleToMove(){
        //given
        final String monsterName = "MONSTER_NAME";
        final String gameName = "game6";
        final String mapName = "map2";
        final String racingName = "RACING_CAR";
        final Point startingPoint = new Point(0,0);

        //when
        addNewCar(template, monsterName, CarType.MONSTER);
        addNewCar(template, racingName, CarType.RACER);
        startNewGame(template, gameName, mapName);

        addCarToGame(template, monsterName, gameName, startingPoint);

        doCarMove(template, gameName, monsterName, new Movement(RIGHT, 0));
        doCarMove(template, gameName, monsterName, new Movement(FORWARD, 1));

        addCarToGame(template, racingName, gameName, startingPoint);
        doCarMove(template, gameName, racingName, new Movement(RIGHT, 0));

        IllegalArgumentException illegalArgumentException = doCarMoveWithExpcetedError(template, gameName, racingName, new Movement(FORWARD, 1));

        List<Car> cars = doCarMove(template, gameName, monsterName, new Movement(FORWARD, 1));
        //then
        ResponseEntity<ApiError> apiErrorResponseEntity = restExceptionHandler.handleBadRequest(illegalArgumentException);

        assertThat(apiErrorResponseEntity.getBody())
                .extracting(ApiError::getMessage)
                .containsExactly("Car was crashed with other");

        assertThat(cars).extracting(Car::getName, Car::isCrashed).contains(tuple("MONSTER_NAME", false));

    }

}
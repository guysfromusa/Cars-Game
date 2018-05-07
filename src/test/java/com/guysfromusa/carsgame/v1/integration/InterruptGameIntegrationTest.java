package com.guysfromusa.carsgame.v1.integration;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.v1.CarApiAware;
import com.guysfromusa.carsgame.v1.GameApiAware;
import com.guysfromusa.carsgame.v1.MapApiAware;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.RIGHT;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class InterruptGameIntegrationTest implements CarApiAware, MapApiAware, GameApiAware {

    @Inject
    private TestRestTemplate template;

    @Inject
    private CarRepository carRepository;

    @Inject
    private GameRepository gameRepository;

    public static final String MAP_CONTENT = "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n";

    @Test
    @Sql("/sql/clean.sql")
    public void shouldInterruptGameWhenNoMoreMoves() {
        //given
        addNewMap(template, new Map("map", MAP_CONTENT));
        addNewCar(template, "car", NORMAL);
        startNewGame(template, "game", "map");
        addCarToGame(template, "car", "game", new Point(2, 2));
        moves("car", "game", FORWARD, RIGHT);

        await().atMost(5, SECONDS).until(this::gameIsArchived);

        assertThat(carRepository.findByName("car")).get()
                .extracting(CarEntity::getGame, CarEntity::getPositionX, CarEntity::getPositionY)
                .containsOnlyNulls();
    }

    private boolean gameIsArchived() {
        return gameRepository.findByName("game")
                .orElseThrow(() -> new IllegalStateException("Game not found"))
                .getGameStatus() == GameStatus.ARCHIVED;
    }

    private List<List<Car>> moves(String zlomek, String gameName, Movement.Operation... operations) {
        return Stream.of(operations)
                .map(operation -> doCarMove(template, gameName, zlomek, new Movement(operation, 1)))
                .collect(Collectors.toList());
    }
}
package com.guysfromusa.carsgame.v1.integration;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.repositories.CarRepository;
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
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class UndoMovementIntegrationTest implements CarApiAware, MapApiAware, GameApiAware {

    @Inject
    private TestRestTemplate template;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Inject
    private AsyncTaskExecutor taskExecutor;

    @Inject
    private CarRepository carRepository;

    public static final String MAP_CONTENT = "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n" +
            "1,1,1,1,1\n";

    @Test
    @Sql("/sql/clean.sql")
    public void shouldPerformUndo() {
        //given
        addNewMap(template, new Map("map", MAP_CONTENT));
        addNewCar(template, "car", NORMAL);
        startNewGame(template, "game", "map");
        addCarToGame(template, "car", "game", new Point(2, 2));
        moves("car", "game", FORWARD, RIGHT, FORWARD, LEFT);

        //when
        undo(template, "game", "car", 4);

        //then
        await().atMost(15, SECONDS).until(() -> carIsInPoint(new Point(2, 2), NORTH));
    }

    private boolean carIsInPoint(Point point, Direction direction) {
        return carRepository.findByName("car")
                .filter(v -> v.getPositionX() == point.getX()
                        && v.getPositionY() == point.getY()
                        && v.getDirection().equals(direction))
                .isPresent();
    }

    private List<List<Car>> moves(String zlomek, String gameName, Movement.Operation... operations) {
        return Stream.of(operations)
                .map(operation -> doCarMove(template, gameName, zlomek, new Movement(operation, 1)))
                .collect(Collectors.toList());
    }
}
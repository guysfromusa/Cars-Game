package com.guysfromusa.carsgame.v1.integration;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.v1.CarApiAware;
import com.guysfromusa.carsgame.v1.GameApiAware;
import com.guysfromusa.carsgame.v1.MapApiAware;
import com.guysfromusa.carsgame.v1.model.*;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
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

    @Test
    @Sql("/sql/clean.sql")
    public void shouldStartGameFromZero() throws InterruptedException {
        //given
        final String mapName = "map1";
        final String mapContent =
                "1,1,1,1,1\n" +
                        "1,1,1,1,1\n" +
                        "1,1,1,1,1\n" +
                        "1,1,1,1,1\n" +
                        "1,1,1,1,1\n";

        final String zlomek = "zlomek";
        final String gameName = "race1";

        //when
        addNewMap(template, new Map(mapName, mapContent));
        addNewCar(template, zlomek, NORMAL);
        startNewGame(template, gameName, mapName);

        //add cars to game
        addCarsToGameAwait(zlomek, gameName);

        //moves F R F L
        move(zlomek, gameName, FORWARD);
        move(zlomek, gameName, RIGHT);
        move(zlomek, gameName, FORWARD);
        move(zlomek, gameName, LEFT);

        java.util.List<UndoNStepPath> undo = undo(template, gameName, zlomek, 4);

        System.out.println(undo);
    }

    private void addCarsToGameAwait(String zlomek, String gameName) {
        List<Future<Car>> addedCars = List.<Callable<Car>>of(
                () -> addCarToGame(template, zlomek, gameName, new Point(2, 2)))
                .map(taskExecutor::submit);
        await().atMost(2, SECONDS)
                .until(() -> addedCars.map(Futures::getUnchecked).toJavaList(), hasSize(1));
    }

    private List<Car> move(String zlomek, String gameName, Movement.Operation operation) {
        return List.ofAll(doCarMove(template, gameName, zlomek, new Movement(operation, 1)));
    }
}
package com.guysfromusa.carsgame.v1.integration;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.v1.CarApiAware;
import com.guysfromusa.carsgame.v1.GameApiAware;
import com.guysfromusa.carsgame.v1.MapApiAware;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import io.vavr.collection.List;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static com.guysfromusa.carsgame.entities.enums.CarType.NORMAL;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.FORWARD;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.LEFT;
import static com.guysfromusa.carsgame.v1.model.Movement.Operation.RIGHT;
import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class IntegrationTest implements CarApiAware, MapApiAware, GameApiAware {

    @Inject
    private TestRestTemplate template;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Inject
    private AsyncTaskExecutor taskExecutor;

    @Inject
    private CarRepository carRepository;

    @Test
    @Sql("/sql/clean.sql")
    public void shouldStartGameFromZero() {
        //given
        final String mapName = "map1";
        final String mapContent = "0,1,1,0,0\n" +
                "0,0,1,0,0\n" +
                "0,0,1,0,0\n" +
                "0,0,1,1,0\n" +
                "0,0,0,1,0\n";

        final String zlomek = "zlomek";
        final String maniek = "maniek";
        final String zygzak = "zygzak";
        final String sally = "sally";
        final String gameName = "race1";

        //when
        addNewMap(template, new Map(mapName, mapContent));
        addNewCar(template, zlomek, NORMAL);
        addNewCar(template, zygzak, NORMAL);
        addNewCar(template, maniek, MONSTER);
        addNewCar(template, sally, NORMAL);
        startNewGame(template, gameName, mapName);

        //add cars to game
        List<Future<Car>> addedCars = List.<Callable<Car>>of(
                () -> addCarToGame(template, zlomek, gameName, new Point(1, 0)),
                () -> addCarToGame(template, zygzak, gameName, new Point(1, 0)),
                () -> addCarToGame(template, maniek, gameName, new Point(3, 4)))
                .map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> addedCars.map(Futures::getUnchecked).toJavaList(), hasSize(3));

        final String zlomekOrZygzak = addedCars
                .map(Futures::getUnchecked)
                .map(Car::getName)
                .filter(Objects::nonNull)
                .find(s -> s.equals(zlomek))
                .getOrElse(zygzak);

        //turn 1
        List<Future<List<Car>>> turn1 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, zlomekOrZygzak, new Movement(RIGHT, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> turn1.map(Futures::getUnchecked).get(0).toJavaList(), hasSize(2));

        //forward 1
        List<Future<List<Car>>> forward1 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, maniek, new Movement(FORWARD, 1))),
                () -> List.ofAll(doCarMove(template, gameName, zlomekOrZygzak, new Movement(FORWARD, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> forward1.map(Futures::getUnchecked).get(0).toJavaList(), hasSize(2));

        //turn 2
        List<Future<List<Car>>> turn2 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, maniek, new Movement(LEFT, 1))),
                () -> List.ofAll(doCarMove(template, gameName, zlomekOrZygzak, new Movement(RIGHT, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> turn2.map(Futures::getUnchecked).get(0).toJavaList(), hasSize(2));

        //forward 2
        List<Future<List<Car>>> forward2 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, maniek, new Movement(FORWARD, 1))),
                () -> List.ofAll(doCarMove(template, gameName, zlomekOrZygzak, new Movement(FORWARD, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> forward2.map(Futures::getUnchecked).get(0).toJavaList(), hasSize(2));

        //turn 3
        List<Future<List<Car>>> turn3 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, maniek, new Movement(RIGHT, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> turn3.map(Futures::getUnchecked).get(0).toJavaList(), hasSize(2));

        //forward 3
        List<Future<List<Car>>> forward3 = List.<Callable<List<Car>>>of(
                () -> List.ofAll(doCarMove(template, gameName, maniek, new Movement(FORWARD, 1))),
                () -> List.ofAll(doCarMove(template, gameName, zlomekOrZygzak, new Movement(FORWARD, 1)))
        ).map(taskExecutor::submit);

        Awaitility.await().atMost(2, SECONDS)
                .until(() -> Futures.getUnchecked(forward3.get(0)).toJavaList(), hasSize(greaterThan(0)));

        //then
        List<CarEntity> allCars = List.ofAll(carRepository.findAll());
        assertThat(allCars).hasSize(4);
        assertThat(allCars).filteredOn(CarEntity::isCrashed)
                .hasSize(1);
        assertThat(allCars).filteredOn(CarEntity::isCrashed)
                .extracting(CarEntity::getName)
                .containsExactly(zlomekOrZygzak);

        assertThat(allCars)
                .filteredOn(carEntity -> nonNull(carEntity.getGame()))
                .extracting(CarEntity::getName)
                .containsExactly(maniek);

    }
}

package com.guysfromusa.carsgame.v1.integration;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.CarApiAware;
import com.guysfromusa.carsgame.v1.GameApiAware;
import com.guysfromusa.carsgame.v1.MapApiAware;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Point;
import io.vavr.API;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class IntegrationTest implements CarApiAware, MapApiAware, GameApiAware {

    @Inject
    private TestRestTemplate template;

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
        addNewCar(template, zlomek, CarType.NORMAL);
        addNewCar(template, maniek, CarType.MONSTER);
        addNewCar(template, zygzak, CarType.RACER);
        addNewCar(template, sally, CarType.NORMAL);
        startNewGame(template, gameName, mapName);
        //TODO add concurrency invocation here
        addCarToGame(template, zlomek, gameName, new Point(2, 0));
        addCarToGame(template, maniek, gameName, new Point(2, 0));
        addCarToGame(template, zygzak, gameName, new Point(2, 0));

        //then
        API.TODO("Update after integration with collision engine");
        List<Car> allCars = Arrays.asList(findAllCars(template));
        assertThat(allCars).hasSize(4);
        List<Car> carsCrashed = allCars.stream().filter(Car::isCrashed).collect(Collectors.toList());
        assertThat(carsCrashed).hasSize(0);
        List<Car> carsInGame = allCars.stream().filter(car -> Objects.nonNull(car.getGame())).collect(Collectors.toList());
        assertThat(carsInGame).hasSize(3);


    }
}

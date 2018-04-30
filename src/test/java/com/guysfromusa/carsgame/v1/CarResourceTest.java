package com.guysfromusa.carsgame.v1;

import com.google.common.collect.ImmutableList;
import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Point;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.awaitility.Duration.FIVE_SECONDS;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by Konrad Rys
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class CarResourceTest implements CarApiAware, GameApiAware, MapApiAware {

    @Inject
    private TestRestTemplate template;

    @Test
    public void shouldReturnCars() {
        //given
        String name = "My-Big-Monster";
        CarType monsterType = MONSTER;
        Awaitility.await().atMost(FIVE_SECONDS)
                .until(() -> addNewCar(template, name, monsterType) != null);

        //when
        ResponseEntity<Car[]> response = template.getForEntity("/v1/cars", Car[].class);

        //then
        assertThat(response.getBody())
                .extracting(Car::getName, Car::getType)
                .contains(tuple(name, MONSTER));
    }

    @Test
    public void shouldAddCar() {
        //given
        String name = "My-Sweet-Car";
        CarType monsterType = MONSTER;

        //when
        Car addedCar = addNewCar(template, name, monsterType);

        //then
        assertThat(ImmutableList.of(addedCar))
                .extracting(Car::getName, Car::getType)
                .containsExactly(tuple("My-Sweet-Car", MONSTER));
    }

    @Test
    public void shouldRemoveCar(){
        //given
        String name = "My-Sweet-Car";
        CarType monsterType = MONSTER;

        String url = String.join("/", "/v1/cars", name);
        Awaitility.await().atMost(FIVE_SECONDS)
                .until(() -> addNewCar(template, name, monsterType) != null);

        //when
        ResponseEntity<Long> removedCarIdResponse = template.exchange(url, DELETE, null, Long.class);

        //then
        assertThat(removedCarIdResponse.getBody()).isGreaterThan(0);
    }

    @Test
    @Sql(value = {"/sql/clean.sql"})
    public void shouldAddCarToGame(){
        //given
        String name = "My-Sweet-Car";
        String game = "game1";
        CarType monsterType = MONSTER;

        String url = String.join("/", "/v1/cars", name, "game", game);
        Point point = new Point(1, 1);

        HttpEntity<Point> requestEntity = new RequestBuilder<Point>().body(point).build();

        //when
        addNewMap(template, new Map("map1", "1,1,1"));
        addNewCar(template, name, monsterType);
        startNewGame(template, "game1", "map1");
        ResponseEntity<Car> carResponseEntity = template.exchange(url, POST, requestEntity, Car.class);

        //then
        Car carResponse = carResponseEntity.getBody();
        assertThat(carResponse.getPosition())
                .extracting(Point::getX, Point::getY)
                .contains(1, 1);
    }


}
package com.guysfromusa.carsgame.v1;

import com.google.common.collect.ImmutableList;
import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.exceptions.ValidationException;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.Comparator;

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
public class CarResourceTest implements CarApiAware {

    private RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Inject
    private TestRestTemplate template;

    @Test
    public void shouldReturnCars() {
        String name = "My-Big-Monster";
        String monsterType = "MONSTER";
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
        String monsterType = "MONSTER";

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
        String monsterType = "MONSTER";

        String url = String.join("/", "/v1/cars", name);
        Awaitility.await().atMost(FIVE_SECONDS)
                .until(() -> addNewCar(template, name, monsterType) != null);

        //when
        Long id = removeCar(template, name);

        //then
        assertThat(id).isGreaterThan(0);
    }

    @Test
    public void shouldAddCarToGame(){
        //given
        String name = "car2";
        String game = "game1";

        String url = String.join("/", "/v1/cars", name, "cars", game);
        Point point = new Point(1, 0);

        //when
        Car car = assignCarToTheGame(template, name, game, point);

        //then
        assertThat(car.getPosition())
                .extracting(Point::getX, Point::getY)
                .contains(1, 0);
    }

    @Test
    public void shouldRejectAdditionCrashedCarToGame(){
        //given
        String name = "car3";
        String game = "game1";

        String url = String.join("/", "/v1/cars", name, "cars", game);
        Point point = new Point(1, 1);

        HttpEntity<Point> requestEntity = new RequestBuilder<Point>().body(point).build();

        //when
        ResponseEntity<IllegalArgumentException> carResponse = template.exchange(url, POST, requestEntity, IllegalArgumentException.class);


        //then
        ResponseEntity<ApiError> responseEntity = restExceptionHandler.handleBadRequest(carResponse.getBody());
        Assertions.assertThat(responseEntity.getBody())
                .extracting(ApiError::getMessage, ApiError::getStatus)
                .containsExactly("Car is aready crashed", "BAD_REQUEST");

    }



}
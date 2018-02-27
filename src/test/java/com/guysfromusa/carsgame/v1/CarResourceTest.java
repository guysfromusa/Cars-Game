package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Car;
import com.sun.deploy.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class CarResourceTest {

    @Inject
    private TestRestTemplate template;

    @Test
    public void shouldReturnCars() {
        String name = "My-Big-Monster";
        String monsterType = "MONSTER";
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() -> addNewCar(name, monsterType) > 0);

        //when
        ResponseEntity<Car[]> response = template.getForEntity("/v1/cars/get-all", Car[].class);

        //then
        assertThat(response.getBody())
                .extracting(Car::getName, Car::getType)
                .contains(tuple(name, monsterType));
    }

    @Test
    public void shouldAddCar() {
        //given
        String name = "My-Sweet-Car";
        String monsterType = "MONSTER";

        //when
        Long addNewCarId = addNewCar(name, monsterType);

        //then
        assertThat(addNewCarId).isGreaterThan(0);

    }

    @Test
    public void shouldRemoveCar(){
        //given
        String name = "My-Sweet-Car";
        String monsterType = "MONSTER";

        String url = String.join("/", "/v1/cars", name, "delete");
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() -> addNewCar(name, monsterType) > 0);

        //when
        ResponseEntity<Long> removedCarIdResponse = template.exchange(url, DELETE, null, Long.class);

        //then
        assertThat(removedCarIdResponse.getBody()).isGreaterThan(0);

    }

    private Long addNewCar(String name, String type){

        String url = String.join("/", "/v1/cars", name, "new", type, "car");

        ResponseEntity<Long> newCarIdResponse = template.exchange(url, POST, null, Long.class);

        return newCarIdResponse.getBody();
    }

}
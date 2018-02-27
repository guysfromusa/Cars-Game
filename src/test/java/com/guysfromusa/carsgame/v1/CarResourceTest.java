package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Car;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;


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
        ResponseEntity<Car[]> response = template.getForEntity("/v1/cars", Car[].class);

        //then
        assertThat(response.getBody())
                .extracting(Car::getName, Car::getType)
                .contains(tuple(name, CarType.MONSTER));
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

        String url = String.join("/", "/v1/cars", name);
        Awaitility.await().atMost(Duration.FIVE_SECONDS).until(() -> addNewCar(name, monsterType) > 0);

        //when
        ResponseEntity<Long> removedCarIdResponse = template.exchange(url, DELETE, null, Long.class);

        //then
        assertThat(removedCarIdResponse.getBody()).isGreaterThan(0);

    }

    private Long addNewCar(String name, String type){
        HttpEntity<Object> requestEntity = new RequestBuilder<>().body(type).build();
        String url = String.join("/", "/v1/cars", name);

        ResponseEntity<Long> newCarIdResponse = template.exchange(url, POST, requestEntity, Long.class);

        return newCarIdResponse.getBody();
    }

}
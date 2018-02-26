package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
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
        //when
        ResponseEntity<Iterable> response = template.getForEntity("/v1/cars/get-all", Iterable.class);

        //then
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    public void shouldAddCar() {
        //given
        String name = "My-Sweet-Car";
        String monsterType = "MONSTER";

        Map<String, String> requestParams = createAdditionRequestParams(name, monsterType);
        HttpEntity<Map> request = new RequestBuilder<Map>().body(requestParams).build();
        //when
        ResponseEntity<Long> newCarIdResponse = template.exchange("/v1/cars/new", POST, request, Long.class);

        //then
        assertThat(newCarIdResponse.getBody()).isGreaterThan(0);

    }

    @Test
    public void shouldRemoveCar(){
        String name = "My-Sweet-Car";
        Map<String, String> body = new HashMap<>();
        body.put("name", name);

        HttpEntity<Map> request = new RequestBuilder<Map>().body(body).build();

        //when
        ResponseEntity<Long> removedCarIdResponse = template
                .exchange("/v1/cars/delete", DELETE, request, Long.class);

        //then
        assertThat(removedCarIdResponse.getBody()).isGreaterThan(0);

    }

    private Map<String, String> createAdditionRequestParams(String name, String monsterType) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", name);
        requestParams.put("type", monsterType);
        return requestParams;
    }

}
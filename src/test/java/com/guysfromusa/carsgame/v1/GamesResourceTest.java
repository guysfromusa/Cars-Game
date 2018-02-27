package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.model.TurnSide.LEFT;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class GamesResourceTest {

    @Inject
    private TestRestTemplate template;

    @Test
    public void whenActionSuccessfulShouldReturnCarPositions() {
        //given
        Movement movement = new Movement();
        movement.setType(Movement.Type.TURN);
        movement.setTurnSide(LEFT);

        HttpEntity<Movement> request = new RequestBuilder<Movement>()
                .body(movement)
                .build();

        //when
        ResponseEntity<Car[]> response = template.postForEntity("/v1/games/1/cars/1/movements", request, Car[].class);

        //then
        assertThat(response.getBody())
                .extracting(Car::getName, Car::getDirection)
                .containsExactly(tuple("car1", null));
    }

}
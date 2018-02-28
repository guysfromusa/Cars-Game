package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.model.Direction.WEST;
import static com.guysfromusa.carsgame.model.TurnSide.LEFT;
import static com.guysfromusa.carsgame.v1.model.Movement.Type.TURN;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class GamesResourceTest implements CarApiAware, MapApiAware {

    @Inject
    private TestRestTemplate template;

    @Test
    @Ignore //impl using db in progress
    public void whenTurnActionSuccessful_thenShouldReturnCarPositions() {
        //given
        addNewMap(template, new Map("mapToPlay", "1,1,1\n1,0,1\n1,0,1"));

        Car car = addNewCar(template, "carToTurn", CarType.MONSTER.name());

        //TODO start game

        //TODO add car to the game

        Movement movement = new Movement();
        movement.setType(TURN);
        movement.setTurnSide(LEFT);

        HttpEntity<Movement> request = new RequestBuilder<Movement>()
                .body(movement)
                .build();

        //when
        String path = String.join("/", "/v1/games", "game1", "cars", car.getName(), "movements");
        ResponseEntity<Car[]> response = template.postForEntity(path, request, Car[].class);

        //then
        assertThat(response.getBody())
                .extracting(Car::getName, Car::getDirection)
                .containsExactly(tuple("carToTurn", WEST));

        assertThat(response.getBody())
                .extracting(Car::getPosition)
                .extracting(Point::getX, Point::getY)
                .containsExactly(tuple(null,null));
    }

}
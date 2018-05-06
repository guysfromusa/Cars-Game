package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.Movement;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
public interface GameApiAware {

    default Game startNewGame(TestRestTemplate template, String gameToPlay, String mapToPlay) {
        String url = String.join("/", "/v1/games", gameToPlay);
        return template.postForObject(url, mapToPlay, Game.class);
    }

    default List<Car> doCarMove(TestRestTemplate testRestTemplate, String game, String carName, Movement movement){
        String url = String.join("/", "/v1/games", game, "cars", carName, "movements");

        HttpEntity<Movement> requestEntity = new RequestBuilder<Movement>().body(movement).build();

        ResponseEntity<Car[]> responseEntity = testRestTemplate.postForEntity(url, requestEntity, Car[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    default IllegalArgumentException doCarMoveWithExpcetedError(TestRestTemplate testRestTemplate, String game, String carName, Movement movement){
        String url = String.join("/", "/v1/games", game, "cars", carName, "movements");

        HttpEntity<Movement> requestEntity = new RequestBuilder<Movement>().body(movement).build();

        ResponseEntity<IllegalArgumentException> responseEntity = testRestTemplate.postForEntity(url, requestEntity, IllegalArgumentException.class);
        return responseEntity.getBody();
    }

}

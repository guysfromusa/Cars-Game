package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpMethod.POST;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public interface CarApiAware {

    default Car addNewCar(TestRestTemplate template, String name, String type){
        HttpEntity<Object> requestEntity = new RequestBuilder<>().body(type).build();
        String url = String.join("/", "/v1/cars", name);

        ResponseEntity<Car> newCarResponse = template.exchange(url, POST, requestEntity, Car.class);

        return newCarResponse.getBody();
    }

    default Car assignCarToTheGame(TestRestTemplate template, String carName, String gameName){
        HttpEntity<Object> requestEntity = new RequestBuilder<>().body(gameName).build();
        String url = String.join("/", "/v1/cars", carName, "game", gameName); //TO CHECK

        ResponseEntity<Car> modifiedCar =  template.exchange(url, POST, requestEntity, Car.class);
        return modifiedCar.getBody();
    }
}

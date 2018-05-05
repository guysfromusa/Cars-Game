package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public interface CarApiAware {

    default Car[] findAllCars(TestRestTemplate template){
        ResponseEntity<Car[]> forEntity = template.getForEntity("/v1/cars", Car[].class);
        return forEntity.getBody();
    }

    default Car addNewCar(TestRestTemplate template, String name, CarType type){
        HttpEntity<Object> requestEntity = new RequestBuilder<>().body(type).build();
        String url = String.join("/", "/v1/cars", name);

        ResponseEntity<Car> newCarResponse = template.exchange(url, POST, requestEntity, Car.class);

        return newCarResponse.getBody();
    }

    default Car addCarToGame(TestRestTemplate template, String carName, String gameName, Point startPoint){

        HttpEntity<Point> requestEntity = new RequestBuilder<Point>().body(startPoint).build();
        String url = String.join("/", "/v1/cars", carName, "game", gameName);

        ResponseEntity<Car> modifiedCar =  template.exchange(url, POST, requestEntity, Car.class);
        return modifiedCar.getBody();
    }

    default Long removeCar(TestRestTemplate template, String carName){

        String url = String.join("/", "/v1/cars", carName);

        ResponseEntity<Long> modifiedCar =  template.exchange(url, DELETE, null, Long.class);
        return modifiedCar.getBody();
    }
}

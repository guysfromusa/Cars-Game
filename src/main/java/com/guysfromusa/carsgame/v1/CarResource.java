package com.guysfromusa.carsgame.v1;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.services.CarService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value= "/v1/cars", produces =  APPLICATION_JSON_UTF8_VALUE)
public class CarResource {

    private final CarService carService;

    @Inject
    public CarResource(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/get-all")
    public Iterable<CarEntity> getAllCars(){
        return carService.loadAllCars();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new")
    public Long addCar(@PathVariable("name") String name, @PathVariable("type") String type){
        CarType carType = CarType.valueOf(type);

        CarEntity newCar = carService.addCar(carType, name);

        return newCar.getId();
    }

    @RequestMapping(method = RequestMethod.DELETE, path= "/delete")
    public Long removeCar(@PathVariable("name") String name){
        return carService.deleteCarByName(name);
    }

}

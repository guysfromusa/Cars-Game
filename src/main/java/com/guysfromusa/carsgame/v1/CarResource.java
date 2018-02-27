package com.guysfromusa.carsgame.v1;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.converters.CarConverter;
import com.guysfromusa.carsgame.v1.model.Car;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import java.util.List;

import static java.util.Collections.singletonList;
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
    public List<Car> getAllCars(){
        List<CarEntity> carEntities = this.carService.loadAllCars();
        return CarConverter.toCars(carEntities);
    }

    @RequestMapping(method = RequestMethod.POST, path = "{name}/new/{type}/car")
    public Long addCar(@PathVariable("name") String name, @PathVariable("type") String type){
        CarType carType = CarType.valueOf(type);

        return carService.addCar(carType, name);
    }

    @RequestMapping(method = RequestMethod.DELETE, path= "{name}/delete")
    public Long removeCar(@PathVariable("name") String name){
        return carService.deleteCarByName(name);
    }

}

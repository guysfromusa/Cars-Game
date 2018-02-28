package com.guysfromusa.carsgame.v1;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.v1.converters.CarConverter.toCar;
import static com.guysfromusa.carsgame.v1.converters.CarConverter.toCars;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Konrad Rys
 */
@RestController
@RequestMapping(value= "/v1/cars", produces =  APPLICATION_JSON_UTF8_VALUE)
@Api(value = "cars", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class CarResource {

    private final CarService carService;

    @Inject
    public CarResource(CarService carService) {
        this.carService = notNull(carService);
    }

    @GetMapping()
    @ApiOperation(value = "Find all available cars", response = List.class)
    public List<Car> getAllCars(){
        List<CarEntity> carEntities = this.carService.loadAllCars();
        return toCars(carEntities);
    }

    @PostMapping(path = "{name}")
    @ApiOperation(value = "Add car", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully added")
    })
    public Car addCar(@PathVariable("name") String name, @RequestBody String type){
        CarType carType = CarType.valueOf(type);

        CarEntity addedCar = carService.addCar(carType, name);
        return toCar(addedCar);
    }

    @PostMapping(path = "{name}/game/{game}")
    @ApiOperation(value = "Add car to given game", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully added to game"),
            @ApiResponse(code = 404, message = "Game not found"),
            @ApiResponse(code = 404, message = "Car not found")
    })
    public Car addCarToGame(@PathVariable("name") String name, @PathVariable("game") String game,
                            @RequestBody Point startingPoint){

        CarEntity addedCar = carService.addCarToGame(name, game, startingPoint);

        return toCar(addedCar);
    }

    @DeleteMapping(path= "{name}")
    @ApiOperation(value = "Remove car", notes = "Operation removes car and returns id of deleted car", response = Long.class)
    public Long removeCar(@PathVariable("name") String name){
        return carService.deleteCarByName(name);
    }

}

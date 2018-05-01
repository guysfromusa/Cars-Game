package com.guysfromusa.carsgame.v1;


import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.utils.StreamUtils;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import io.swagger.annotations.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.v1.validator.CarGameAdditionValidator.*;
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

    private final ConversionService conversionService;

    @Inject
    public CarResource(CarService carService, ConversionService conversionService) {
        this.carService = notNull(carService);
        this.conversionService = notNull(conversionService);
    }

    @GetMapping()
    @ApiOperation(value = "Find all available cars", response = List.class)
    public List<Car> getAllCars(){
        List<CarEntity> carEntities = this.carService.loadAllCars();
        return StreamUtils.convert(carEntities,
                carEntity -> conversionService.convert(carEntity, Car.class));
    }

    @PostMapping(path = "{name}")
    @ApiOperation(value = "Add car", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car successfully added")
    })
    public Car addCar(@PathVariable("name") String name, @RequestBody CarType carType){
        CarEntity addedCar = carService.addCar(carType, name);
        return conversionService.convert(addedCar, Car.class);
    }

    @PostMapping(path = "{name}/game/{game}")
    @ApiOperation(value = "Add car to given game", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully added to game"),
            @ApiResponse(code = 400, message = "Game not found"),
            @ApiResponse(code = 400, message = "Car not found"),
            @ApiResponse(code = 400, message = CAR_CRASHED_MESSAGE),
            @ApiResponse(code = 400, message = CAR_EXISTS_IN_GAME_MESSAGE),
            @ApiResponse(code = 400, message = WRONG_STARTING_POINT_MESSAGE)
    })
    public Car addCarToGame(@ApiParam(name = "name", value="Car name") @PathVariable("name") String name,
                            @ApiParam(name = "game", value="Game name") @PathVariable("game") String game,
                            @RequestBody Point startingPoint){

        CarEntity addedCar = carService.addCarToGame(name, game, startingPoint);

        return conversionService.convert(addedCar, Car.class);
    }

    @DeleteMapping(path= "{name}")
    @ApiOperation(value = "Remove car", notes = "Operation removes car and returns id of deleted car", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Car successfully removed")
    })
    public Long removeCar(@ApiParam(name = "name", value="Car name to be deleted") @PathVariable("name") String name){
        return carService.deleteCarByName(name);
    }

    @PostMapping(path= "{name}/fix")
    @ApiOperation(value = "Repair crashed car", response = Car.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Car successfully repaired"),
        @ApiResponse(code = 400, message = "Car with given name does not exist")
    })
    public Car repeairCar(@PathVariable("name") String name){
        CarEntity carEntity = carService.repairCar(name);
        return conversionService.convert(carEntity, Car.class);
    }

}

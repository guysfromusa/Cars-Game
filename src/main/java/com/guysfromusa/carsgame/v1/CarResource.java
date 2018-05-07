package com.guysfromusa.carsgame.v1;


import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.commands.AddCarToGameCommand;
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

import static com.guysfromusa.carsgame.control.MessageType.ADD_CAR_TO_GAME;
import static com.guysfromusa.carsgame.validator.CarNotCrashedValidator.CAR_CRASHED_MESSAGE;
import static com.guysfromusa.carsgame.validator.CarNotInGameValidator.CAR_EXISTS_IN_GAME_MESSAGE;
import static com.guysfromusa.carsgame.validator.StartingPointOccupiedValidator.STARTING_POINT_OCCUPIED_MESSAGE;
import static com.guysfromusa.carsgame.validator.StartingPointOnMapValidator.WRONG_STARTING_POINT_MESSAGE;
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

    private final CommandProducer commandProducer;

    @Inject
    public CarResource(CarService carService,
                       ConversionService conversionService,
                       CommandProducer commandProducer) {
        this.carService = notNull(carService);
        this.conversionService = notNull(conversionService);
        this.commandProducer = notNull(commandProducer);
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

    @PostMapping(path = "{name}/repair")
    @ApiOperation(value = "Repair car", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Car successfully repaired")
    })
    public Car repairCar(@PathVariable("name") String carName){
        CarEntity carEntity = carService.repairCar(carName);
        return  conversionService.convert(carEntity, Car.class);
    }

    @PostMapping(path = "{name}/game/{game}")
    @ApiOperation(value = "Add car to given game", response = Car.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Car successfully added to game"),
            @ApiResponse(code = 400, message = "Game not found"),
            @ApiResponse(code = 400, message = "Car not found"),
            @ApiResponse(code = 400, message = CAR_CRASHED_MESSAGE),
            @ApiResponse(code = 400, message = CAR_EXISTS_IN_GAME_MESSAGE),
            @ApiResponse(code = 400, message = WRONG_STARTING_POINT_MESSAGE),
            @ApiResponse(code = 400, message = STARTING_POINT_OCCUPIED_MESSAGE)
    })
    public Car addCarToGame(@ApiParam(name = "name", value = "Car name") @PathVariable("name") String carName,
                            @ApiParam(name = "game", value = "Game name") @PathVariable("game") String gameName,
                            @RequestBody Point startingPoint){

        AddCarToGameCommand addCarToGameCommand = AddCarToGameCommand.builder()
                .carName(carName)
                .gameName(gameName)
                .messageType(ADD_CAR_TO_GAME)
                .startingPoint(startingPoint)
                .build();

        CarEntity addedCar = commandProducer.scheduleCommand(addCarToGameCommand);
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

}

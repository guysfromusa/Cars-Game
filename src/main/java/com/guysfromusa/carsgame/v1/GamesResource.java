package com.guysfromusa.carsgame.v1;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameService;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static com.guysfromusa.carsgame.v1.converters.CarConverter.toCars;
import static com.guysfromusa.carsgame.v1.converters.GameConverter.toGame;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "games", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {

    private final Map<Movement.Type, MovementStrategy> movementStrategyMap = Maps.newEnumMap(Movement.Type.class);

    private final CarService carService;

    private final GameService gameService;

    @Inject
    public GamesResource(List<MovementStrategy> movementStrategies, CarService carService, GameService gameService){
        this.carService = notNull(carService);
        this.gameService = notNull(gameService);
        notEmpty(movementStrategies)
                .forEach(strategy -> movementStrategyMap.put(strategy.getType(), strategy));
    }

    @PostMapping(path = "{game}/cars/{car}/movements", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Perform a movement of the car", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful movement of the car"),
            @ApiResponse(code = 404, message = "Game not found")})
    public List<Car> newMovement(@PathVariable String game, @PathVariable("car") String carName, @RequestBody /*@Validated*/ Movement newMovement){

        movementStrategyMap.get(newMovement.getType()).execute(game, carName, newMovement);

        List<CarEntity> carsInGame = carService.findCars(game);
        return toCars(carsInGame);
    }

    @ApiOperation(value = "Starts the game with the given Map")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game was successfully started")})
    @PostMapping(path = "{gameName}")
    public Game startNewGame(@PathVariable("gameName") String gameName, @RequestBody String mapName){
        GameEntity gameEntity = gameService.startNewGame(gameName, mapName);
        return toGame(gameEntity);
    }

}

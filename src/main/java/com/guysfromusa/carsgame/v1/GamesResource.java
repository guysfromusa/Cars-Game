package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.control.*;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.game_state.GameStateTracker;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameService;
import com.guysfromusa.carsgame.utils.StreamUtils;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.GameStatusDto;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import io.swagger.annotations.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "games", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {

    private final CarService carService;

    private final GameService gameService;

    private final ConversionService conversionService;

    private final GameController gameController;

    @Inject
    public GamesResource(List<MovementStrategy> movementStrategies, CarService carService, GameService gameService,
                         ConversionService conversionService, GameController gameController, GameStateTracker gameStateTracker){
        this.carService = notNull(carService);
        this.gameService = notNull(gameService);
        this.conversionService = notNull(conversionService);
        this.gameController = notNull(gameController);
    }

    @PostMapping(path = "{game}/cars/{car}/movements", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Perform a movement of the car", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful movement of the car"),
            @ApiResponse(code = 404, message = "Game not found"),
            @ApiResponse(code = 404, message = "Car not found")
    })
    public List<Car> newMovement(@PathVariable String game, @PathVariable("car") String carName, @RequestBody /*@Validated*/ Movement newMovement) throws InterruptedException {
        MovementMessage message = new MovementMessage();
        message.setGameName(game);
        message.setMessageType(MessageType.MOVE);
        message.setCarName(carName);

        gameController.handle(message);

        List<CarEntity> carsInGame = carService.findCars(game);
        return StreamUtils.convert(carsInGame,carEntity -> conversionService.convert(carEntity, Car.class));
    }

    @ApiOperation(value = "Starts the game with the given Map")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game was successfully started"),
            @ApiResponse(code = 404, message = "Map not found"),
            @ApiResponse(code = 409, message = "Game already exists")

    })


    @PostMapping(path = "{gameName}")
    public Game startNewGame(@PathVariable("gameName") String gameName, @RequestBody String mapName) throws InterruptedException {
        GameEntity gameEntity = gameService.startNewGame(gameName, mapName);
        Game game = conversionService.convert(gameEntity, Game.class);

        NewGameMessage message = new NewGameMessage();
        message.setGameName(gameEntity.getName());
        String content = gameEntity.getMap().getContent();

        Integer[][] mapMatrixContent = GameMapUtils.getMapMatrixContent(content);
        message.setMapContent(mapMatrixContent);
        gameController.handle(message);

        return game;
    }

    @GetMapping(path = "{gameName}")
    @ApiOperation(value = "Check status for the given game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status of the game"),
            @ApiResponse(code = 404, message = "Game not found")

    })
    public GameStatusDto checkStatus(@ApiParam(name = "gameName", value = "name of the game", required = true)
                                                @PathVariable("gameName") String gameName){
        GameStatus status = gameService.getStatus(gameName);
        return conversionService.convert(status, GameStatusDto.class);
    }



}

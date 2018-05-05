package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.MoveCommand;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.services.GameService;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Game;
import com.guysfromusa.carsgame.v1.model.GameStatusDto;
import com.guysfromusa.carsgame.v1.model.MovementResult;
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

    private final GameService gameService;

    private final ConversionService conversionService;

    private final ActiveGamesContainer activeGamesContainer;

    private final CommandProducer commandProducer;

    @Inject
    public GamesResource(List<MovementStrategy> movementStrategies, CarService carService, GameService gameService, ConversionService conversionService, ActiveGamesContainer activeGamesContainer, CommandProducer commandProducer) {
        this.gameService = notNull(gameService);
        this.conversionService = notNull(conversionService);
        this.activeGamesContainer = notNull(activeGamesContainer);
        this.commandProducer = notNull(commandProducer);
    }

    @PostMapping(path = "{game}/cars/{car}/movements", consumes = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Perform a movement of the car", response = MovementResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful movement of the car"),
            @ApiResponse(code = 404, message = "Game not found"),
            @ApiResponse(code = 404, message = "Car not found")
    })
    public List<Car> newMovement(@PathVariable String game, @PathVariable("car") String carName, @RequestBody /*@Validated*/ Movement newMovement) {
        MoveCommand moveCommand = MoveCommand.builder()
                .carName(carName)
                .gameName(game)
                .messageType(MessageType.MOVE)
                .movement(newMovement)
                .build();

        return commandProducer.scheduleCommand(game, moveCommand);

    }

    @ApiOperation(value = "Starts the game with the given Map")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Game was successfully started"),
            @ApiResponse(code = 404, message = "Map not found"),
            @ApiResponse(code = 409, message = "Game already exists")

    })
    @PostMapping(path = "{gameName}")
    public Game startNewGame(@PathVariable("gameName") String gameName, @RequestBody String mapName) {

        GameEntity gameEntity = gameService.startNewGame(gameName, mapName);
        activeGamesContainer.addNewGame(gameEntity.getName());
        return conversionService.convert(gameEntity, Game.class);
    }

    @GetMapping(path = "{gameName}")
    @ApiOperation(value = "Check status for the given game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status of the game"),
            @ApiResponse(code = 404, message = "Game not found")

    })
    public GameStatusDto checkStatus(@ApiParam(name = "gameName", value = "name of the game", required = true)
                                     @PathVariable("gameName") String gameName) {
        GameStatus status = gameService.getStatus(gameName);
        return conversionService.convert(status, GameStatusDto.class);
    }


}

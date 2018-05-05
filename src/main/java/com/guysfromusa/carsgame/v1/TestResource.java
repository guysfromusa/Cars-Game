package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.v1.model.Car;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Sebastian Mikucki, 30.04.18
 * @deprecated this class will be removed after agreed final solution
 */
//TODO delete as only POC
@Deprecated
@RestController
@RequestMapping(value = "/v1/push", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "control", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class TestResource {

    private final CommandProducer commandProducer;

    @Autowired
    public TestResource(CommandProducer commandProducer) {
        this.commandProducer = commandProducer;
    }

    @GetMapping(value = "{gameName}")
    @ApiOperation(value = "simmulate message")
    public List<Car> startNewGame(@PathVariable("gameName") String gameName) {
//        Command move = new AddCarToGameCommand();
//        move.setGameName(gameName);
//        move.setCarName("car1");
//        move.setMessageType(MessageType.ADD_CAR_TO_GAME);

        return commandProducer.scheduleCommand(gameName, null);
    }

}

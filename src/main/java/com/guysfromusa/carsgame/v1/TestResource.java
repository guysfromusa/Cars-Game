package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.control.GameController;
import com.guysfromusa.carsgame.control.Message;
import com.guysfromusa.carsgame.control.MessageType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    private final GameController gameController;

    @Autowired
    public TestResource(GameController gameController) {
        this.gameController = gameController;
    }

    @GetMapping(value = "{gameName}")
    @ApiOperation(value = "simmulate message")
    public String startNewGame(@PathVariable("gameName") String gameName) throws InterruptedException, ExecutionException {
        Message m = new Message();
        m.setGameName(gameName);
        m.setMessageType(MessageType.MOVE);
        CompletableFuture<String> future = m.getFuture();
        gameController.handle(m);
        return future.get();
    }

}
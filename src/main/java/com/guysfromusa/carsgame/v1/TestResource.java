package com.guysfromusa.carsgame.v1;

import com.google.common.util.concurrent.Futures;
import com.guysfromusa.carsgame.control.Message;
import com.guysfromusa.carsgame.control.MessageDispatcher;
import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.game_state.GameStateTracker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    private final GameStateTracker gameStateTracker;

    private final MessageDispatcher messageDispatcher;

    @Autowired
    public TestResource(GameStateTracker gameStateTracker) {
        this.gameStateTracker = gameStateTracker;
    }

    @GetMapping(value = "{gameName}")
    @ApiOperation(value = "simmulate message")
    public String startNewGame(@PathVariable("gameName") String gameName) {
        Message move = new Message();
        move.setGameName(gameName);
        move.setMessageType(MessageType.MOVE);

        return Optional.ofNullable(gameStateTracker.getGameState(gameName)) //could be the game is already finished
                .map(state -> {
                    CompletableFuture<String> result = state.addMovementToExecute(move);
                    messageDispatcher.queuesNotEmptyBarrier.await(); //FIXME find a better place or use Spring notification, await here is not a best idea
                    return result;
                })
                .map(Futures::getUnchecked)
                .orElse("{status:error}");
    }

}

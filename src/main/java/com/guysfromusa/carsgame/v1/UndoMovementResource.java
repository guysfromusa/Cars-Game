package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.services.UndoMovementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Dominik Zurek 02.05.2018
 */
@RestController
@RequestMapping(value = "/v1/back-movements", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "back-movements", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class UndoMovementResource {

    private final UndoMovementService undoMovementService;

    @Autowired
    public UndoMovementResource(UndoMovementService undoMovementService) {
        this.undoMovementService = notNull(undoMovementService);
    }

    @ApiOperation(value = "Find history", response = List.class)
    @GetMapping("/{gameId}/{carName}/{numberOfStepBack}")
    public List<CarEntity> findMovementHistory(@PathVariable("gameId") String gameId,
                                               @PathVariable("carName") String carName,
                                               @PathVariable("numberOfStepBack") int numberOfStepBack) throws ExecutionException, InterruptedException {

        return undoMovementService.doNMoveBack(gameId, carName, numberOfStepBack);
    }

}
package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.services.MovementsHistoryService;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Dominik Zurek 28.04.2018
 */

@RestController
@RequestMapping(value= "/v1/movements-history", produces =  APPLICATION_JSON_UTF8_VALUE)
@Api(value = "movements-history", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class MovementsHistoryResource {

    private final MovementsHistoryService service;

    @Autowired
    public MovementsHistoryResource(MovementsHistoryService service) {
        this.service = notNull(service);
    }


    @ApiOperation(value = "Find history", response = List.class)
    @GetMapping(params = {"gameIds", "carNames", "limitOfRecentStep"})
    public List<MovementHistory> findMovementHistory(@ApiParam(value = "game's ids needed to find move history")
                                                        @RequestParam(value = "gameIds", required = false) List<String> gameIds,
                                                     @ApiParam(value = "car's names needed to find move history")
                                                        @RequestParam(value = "carNames", required = false) List<String> carNames,
                                                     @ApiParam(value = "number of previous step")
                                                         @RequestParam(value = "limitOfRecentStep", required = false) Optional<Integer> limitOfRecentStep){
        return service.findMovementsHistory(gameIds, carNames, limitOfRecentStep);
    }
}

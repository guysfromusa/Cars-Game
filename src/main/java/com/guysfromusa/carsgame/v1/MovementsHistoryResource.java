package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.services.MovementsHistoryService;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Dominik Zurek 28.04.2018
 */

@RestController
@RequestMapping(value= "/v1/movements-history", produces =  APPLICATION_JSON_UTF8_VALUE)
@Api(value = "history", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class MovementsHistoryResource {

    private final MovementsHistoryService service;

    @Autowired
    public MovementsHistoryResource(MovementsHistoryService service) {
        this.service = notNull(service);
    }

    @ApiOperation(value = "Find all available movements history", response = List.class)
    @GetMapping(value = "/{gameIds}/{carNames}/{limitOfRecentStep}")
    public List<MovementHistory> findMovementHistory(@PathVariable("gameIds") List<String> gameIds,
                                                     @PathVariable("carNames") List<String> carNames,
                                                     @PathVariable("limitOfRecentStep") int limitOfRecentStep){
        return service.findMovementsHistory(gameIds, carNames, limitOfRecentStep);
    }
}

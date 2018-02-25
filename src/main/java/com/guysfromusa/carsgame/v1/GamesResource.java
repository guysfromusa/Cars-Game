package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.v1.model.Action;
import com.guysfromusa.carsgame.v1.model.CarPosition;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {


    @RequestMapping(value = "{game}/cars/{car}", method = PUT, consumes = APPLICATION_JSON_UTF8_VALUE)
    public List<CarPosition> moveCar(@PathVariable String game, @PathVariable String car, @RequestBody /*@Validated*/ Action newMessage){

        throw new NotImplementedException("implement me");
    }

}

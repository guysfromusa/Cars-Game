package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.v1.model.CarPosition;
import com.guysfromusa.carsgame.v1.model.Movement;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {


    @RequestMapping(value = "{game}/cars/{car}/movements", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public List<CarPosition> moveCar(@PathVariable String game, @PathVariable String car, @RequestBody /*@Validated*/ Movement newMovement){



        return singletonList(new CarPosition("car1", new Point(0,1), NORTH));
    }

}

package com.guysfromusa.carsgame.v1;

import com.google.common.collect.Maps;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.services.CarService;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Movement;
import com.guysfromusa.carsgame.v1.movement.MovementStrategy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static com.guysfromusa.carsgame.v1.converters.CarConverter.toCars;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {

    private final Map<Movement.Type, MovementStrategy> movementStrategyMap = Maps.newEnumMap(Movement.Type.class);

    private final CarService carService;

    @Inject
    public GamesResource(List<MovementStrategy> movementStrategies, CarService carService){
        this.carService = notNull(carService);
        notEmpty(movementStrategies)
                .forEach(strategy -> movementStrategyMap.put(strategy.getType(), strategy));
    }

    @RequestMapping(value = "{game}/cars/{car}/movements", method = POST, consumes = APPLICATION_JSON_UTF8_VALUE)
    public List<Car> newMovement(@PathVariable String game, @PathVariable("car") String carName, @RequestBody /*@Validated*/ Movement newMovement){

        movementStrategyMap.get(newMovement.getType()).execute(game, carName, newMovement);

        List<CarEntity> carsInGame = carService.findCars(game);
        return toCars(carsInGame);
    }

}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.model.TurnSide;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Service
public class CarService {

    @Transactional
    public List<CarEntity> findCars(String game) {
        return singletonList(new CarEntity(){{ //FIXME implement me
            this.setName("car1");
        }});
    }

    @Transactional
    public void turnCar(String game, String carName, TurnSide turnSide) {
        //TODO implement me
        //update cars
        //update movements
    }
}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    @Inject
    public CarService(CarRepository carRepository){
        this.carRepository = notNull(carRepository);
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findCars(String game) {
        return singletonList(new CarEntity(){{ //FIXME implement me
            this.setName("car1");
        }});
    }

    @Transactional
    public void turnCar(String game, String carName, TurnSide turnSide) {
        //TODO implement me
        //CarEntity car = carRepository.findByName(carName); uncomment me once adding cars will be reedy

        //update movements
    }
}

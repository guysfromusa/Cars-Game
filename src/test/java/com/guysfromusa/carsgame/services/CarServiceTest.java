package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.repositories.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringContextConfiguration.class)
public class CarServiceTest {

    @Inject
    private CarRepository carRepository;

    @Inject
    private CarService carService;

    @Test
    public void shouldAddNewCar(){
        //given
        CarType monsterCarType = CarType.MONSTER;
        String carName = "My-First-CarEntity-Added";

        //when
        Long addedCarId = addCar(monsterCarType, carName);

        //then
        assertThat(addedCarId).isGreaterThan(0);
    }

    @Test
    public void shouldDeleteCar(){
        //given
        String carName = "My-First-To-Be-Deleted";
        CarType monsterCarType = CarType.MONSTER;

        //when
        addCar(monsterCarType, carName);
        long deletedCarId = carService.deleteCarByName(carName);

        //then
        assertTrue(deletedCarId > 0);
    }

    @Test
    public void shouldLoadAtLeastOneCar(){
        //given
        String carName = "My-Second-Car";
        CarType monsterCarType = CarType.MONSTER;

        //when
        addCar(monsterCarType, carName);
        Iterable<CarEntity> carEntities = carService.loadAllCars();

        //then
        assertThat(carEntities).isNotEmpty();

    }

    private Long addCar(CarType carType, String carName){
        return carService.addCar(carType, carName);
    }

}
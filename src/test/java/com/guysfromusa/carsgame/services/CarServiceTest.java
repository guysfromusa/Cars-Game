package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.repositories.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void shouldAddNewCar(){
        //given
        CarType monsterCarType = MONSTER;
        String carName = "My-First-CarEntity-Added";

        //when
        addCar(monsterCarType, carName);

        //then
        ArgumentCaptor<CarEntity> carEntityArgumentCaptor = ArgumentCaptor.forClass(CarEntity.class);
        Mockito.verify(carRepository).save(carEntityArgumentCaptor.capture());

        CarEntity capturedCarEntity = carEntityArgumentCaptor.getValue();
        assertEquals(carName, capturedCarEntity.getName());
    }

    @Test
    public void shouldDeleteCar(){
        //given
        String givenCarName = "My-First-To-Be-Deleted";

        //when
        carService.deleteCarByName(givenCarName);

        //then
        ArgumentCaptor<String> carNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(carRepository).deleteByName(carNameArgumentCaptor.capture());
        String capturedCarName = carNameArgumentCaptor.getValue();

        assertEquals(givenCarName, capturedCarName);
    }

    @Test
    public void shouldLoadAtLeastOneCar(){
        //given
        String carName = "My-Second-Car";
        List<CarEntity> cars = singletonList(new CarEntity() {{
            this.setName(carName);
        }});
        when(carRepository.findAll()).thenReturn(cars);

        //when
        Iterable<CarEntity> carEntities = carService.loadAllCars();

        //then
        assertThat(carEntities).isNotEmpty().extracting(CarEntity::getName).contains(carName);

    }


    private Long addCar(CarType carType, String carName){
        return carService.addCar(carType, carName);
    }



}
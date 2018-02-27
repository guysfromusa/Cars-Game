package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.repositories.BaseRepositoryTest;
import com.guysfromusa.carsgame.repositories.CarRepository;
import org.apache.commons.collections.iterators.SingletonListIterator;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.verify;


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
        Mockito.verify(carRepository).save(argThat(new ArgumentMatcher<CarEntity>() {
            @Override
            public boolean matches(Object argument) {
                CarEntity carEntity = (CarEntity) argument;
                return Objects.equals(carEntity.getName(), carName) && Objects.equals(carEntity.getCarType(), MONSTER);
            }
        }));
    }

    @Test
    public void shouldDeleteCar(){
        //given
        String givenCarName = "My-First-To-Be-Deleted";

        //when
        carService.deleteCarByName(givenCarName);

        //then
        Mockito.verify(carRepository).deleteByName(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(Object argument) {
                String carName = (String) argument;
                return Objects.equals(carName, givenCarName);
            }
        }));
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
package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.entities.CarEntity;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public class CarRepositoryTest extends BaseRepositoryTest {

    @Inject
    private CarRepository carRepository;

    @Test
    @DatabaseSetup("/carRepository_shouldFindACarByName.xml")
    public void shouldFindACarByName() {
        //given

        //when
        CarEntity gotCar = carRepository.findByName("carName2");

        //then
        assertThat(gotCar).isNotNull();
        assertThat(gotCar.getId()).isEqualTo(2);
        assertThat(gotCar.getName()).isEqualTo("carName2");
    }
}
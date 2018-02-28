package com.guysfromusa.carsgame.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.entities.CarEntity;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


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

    @Test
    @DatabaseSetup("/carRepository_shouldFindACarByGameAndName.xml")
    public void shouldFindACarByGameAndName() {
        //given

        //when
        CarEntity gotCar = carRepository.findByGameAndName("game2", "carName2")
                .orElseThrow(() -> new IllegalStateException("no car found"));
        Optional<CarEntity> carEntityOptional = carRepository.findByGameAndName("game2", "carName2");

        //then
        assertThat(gotCar.getId()).isEqualTo(2);
        assertThat(gotCar.getName()).isEqualTo("carName2");
    }

    @Test
    @DatabaseSetup("/carRepository_shouldFindAllCarsByGame.xml")
    public void shouldFindAllCarsByGame() {
        //given

        //when
        List<CarEntity> gotCars = carRepository.findByGame("game2");

        //then
        assertThat(gotCars)
                .extracting(CarEntity::getName)
                .containsExactlyInAnyOrder("carName2", "carName4");
    }

}
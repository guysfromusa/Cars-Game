package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static com.guysfromusa.carsgame.entities.enums.CarType.RACER;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Konrad Rys, 03.05.2018
 */
public class CollisionMonitorTest {

    private CollisionMonitor collisionMonitor = new CollisionMonitor();

    @Test
    public void whenRacingCarOnOnePoint_shouldMarkCarsCrashed(){
        //given
        CarDto car1 = createCar(1, 1, "car1", RACER);
        CarDto car2 = createCar(1, 1, "car2", RACER);
        List<CarDto> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1", "car2");
    }

    @Test
    public void whenTruckCollidedWithNormalCar_shouldMarkNormalCarCrashed(){
        //given
        CarDto car1 = createCar(1, 1, "car1", RACER);
        CarDto car2 = createCar(1, 1, "car2", MONSTER);
        List<CarDto> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1");
    }

    @Test
    public void whenNormalCarAndMonsterOnOnePoint_shouldMarkCarsCrashed(){
        //given
        CarDto car1 = createCar(1, 1, "car1", MONSTER);
        CarDto car2 = createCar(1, 1, "car2", MONSTER);
        List<CarDto> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1", "car2");
    }

    @Test
    public void whenTwoMonstersOnDifferentPoint_shouldNotMarkCarsCrashed(){
        //given
        CarDto car1 = createCar(2, 1, "car1", MONSTER);
        CarDto car2 = createCar(1, 1, "car2", MONSTER);
        List<CarDto> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).isEmpty();
    }

    private CarDto createCar(Integer posX, Integer posY, String carName, CarType carType){
        return CarDto.builder()
                .position(new Point(posX, posY))
                .name(carName)
                .type(carType).build();
    }

}
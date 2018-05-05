package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.v1.model.Car;
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
        Car car1 = createCar(1, 1, "car1", RACER);
        Car car2 = createCar(1, 1, "car2", RACER);
        List<Car> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1", "car2");
    }

    @Test
    public void whenTruckCollidedWithNormalCar_shouldMarkNormalCarCrashed(){
        //given
        Car car1 = createCar(1, 1, "car1", RACER);
        Car car2 = createCar(1, 1, "car2", MONSTER);
        List<Car> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1");
    }

    @Test
    public void whenNormalCarAndMonsterOnOnePoint_shouldMarkCarsCrashed(){
        //given
        Car car1 = createCar(1, 1, "car1", MONSTER);
        Car car2 = createCar(1, 1, "car2", MONSTER);
        List<Car> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).containsOnly("car1", "car2");
    }

    @Test
    public void whenTwoMonstersOnDifferentPoint_shouldNotMarkCarsCrashed(){
        //given
        Car car1 = createCar(2, 1, "car1", MONSTER);
        Car car2 = createCar(1, 1, "car2", MONSTER);
        List<Car> cars = asList(car1, car2);

        //when
        Set<String> crashedCarNames = collisionMonitor.getCrashedCarNames(cars);

        //then
        assertThat(crashedCarNames).isEmpty();
    }

    private Car createCar(Integer posX, Integer posY, String carName, CarType carType){
        Point point = new Point();
        point.setX(posX);
        point.setY(posY);

        return Car.builder()
        .position(point)
        .name(carName)
        .type(carType).build();
    }

}
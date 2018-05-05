package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by Konrad Rys, 03.05.2018
 */
@Component
public class CollisionMonitor {

    public Set<String> getCrashedCarNames(List<Car> cars){
        Set<String> crashedCars = new HashSet<>();

        Map<Point, List<Car>> potentialCollisionPoints = cars.stream()
                .filter(isCarNotRemovedFromGameMap())
                .collect(groupingBy(o -> o.getPosition()));

        potentialCollisionPoints.entrySet().stream().filter(isCollisionPoint())
                .map(this::resolveSinglePointCollision)
                .forEach(crashedCars::addAll);

        return crashedCars;
    }

    private List<String> resolveSinglePointCollision(Map.Entry<Point, List<Car>> pointWithCars){
        List<String> crashedCarNamesForPoint = new ArrayList<>();

        List<Car> carsOnCollision = pointWithCars.getValue();
        Map<Integer, List<Car>> carsByWeightRatio = carsOnCollision.stream()
                .collect(groupingBy(o -> o.getType().getWeightRatio()));

        Optional<Integer> maxWeightRatioCar = carsByWeightRatio.keySet().stream()
                .max(Comparator.naturalOrder());

        Integer biggestCarWeightRatio = maxWeightRatioCar.get();
        crashedCarNamesForPoint.addAll(getCrashedAllSmallerCars(biggestCarWeightRatio, carsByWeightRatio));
        crashedCarNamesForPoint.addAll(getTrucksCrashedIfMoreThanOne(carsByWeightRatio.get(biggestCarWeightRatio)));

        return crashedCarNamesForPoint;
    }

    private List<String> getTrucksCrashedIfMoreThanOne(List<Car> cars) {
        if(isMoreThanOneCar(cars)){
            return cars.stream().map(Car::getName).collect(toList());
        }
        return Collections.emptyList();
    }

    private boolean isMoreThanOneCar(List<Car> cars) {
        return cars.size() > 1;
    }

    private List<String> getCrashedAllSmallerCars(Integer biggestCarWeightRatio, Map<Integer, List<Car>> carsByWeightRatio) {
        return carsByWeightRatio.entrySet().stream()
                .filter(weightCarEntryList -> weightCarEntryList.getKey() != biggestCarWeightRatio)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(Car::getName)
                .collect(toList());
    }

    private Predicate<Map.Entry<Point, List<Car>>> isCollisionPoint(){
        return pointListEntry -> isMoreThanOneCar(pointListEntry.getValue());
    }

    private Predicate<Car> isCarNotRemovedFromGameMap(){
        return car -> Optional.ofNullable(car).map(Car::getPosition).isPresent();
    }
}

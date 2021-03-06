package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by Konrad Rys, 03.05.2018
 */
@Component
public class CollisionMonitor {

    public Set<String> getCrashedCarNames(List<CarDto> cars){
        Set<String> crashedCars = new HashSet<>();

        Map<Point, List<CarDto>> potentialCollisionPoints = cars.stream()
                .filter(isCarNotRemovedFromGameMap())
                .collect(groupingBy(CarDto::getPosition));

        potentialCollisionPoints.entrySet().stream().filter(isCollisionPoint())
                .map(this::resolveSinglePointCollision)
                .forEach(crashedCars::addAll);

        return crashedCars;
    }

    private List<String> resolveSinglePointCollision(Map.Entry<Point, List<CarDto>> pointWithCars){
        List<String> crashedCarNamesForPoint = new ArrayList<>();

        List<CarDto> carsOnCollision = pointWithCars.getValue();
        Map<Integer, List<CarDto>> carsByWeightRatio = carsOnCollision.stream()
                .collect(groupingBy(o -> o.getType().getWeightRatio()));

        Optional<Integer> maxWeightRatioCar = carsByWeightRatio.keySet().stream()
                .max(Comparator.naturalOrder());

        Integer biggestCarWeightRatio = maxWeightRatioCar.get();
        crashedCarNamesForPoint.addAll(getCrashedAllSmallerCars(biggestCarWeightRatio, carsByWeightRatio));
        crashedCarNamesForPoint.addAll(getTrucksCrashedIfMoreThanOne(carsByWeightRatio.get(biggestCarWeightRatio)));

        return crashedCarNamesForPoint;
    }

    private List<String> getTrucksCrashedIfMoreThanOne(List<CarDto> cars) {
        if(isMoreThanOneCar(cars)){
            return cars.stream().map(CarDto::getName).collect(toList());
        }
        return Collections.emptyList();
    }

    private boolean isMoreThanOneCar(List<CarDto> cars) {
        return cars.size() > 1;
    }

    private List<String> getCrashedAllSmallerCars(Integer biggestCarWeightRatio, Map<Integer, List<CarDto>> carsByWeightRatio) {
        return carsByWeightRatio.entrySet().stream()
                .filter(weightCarEntryList -> weightCarEntryList.getKey() != biggestCarWeightRatio)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(CarDto::getName)
                .collect(toList());
    }

    private Predicate<Map.Entry<Point, List<CarDto>>> isCollisionPoint(){
        return pointListEntry -> isMoreThanOneCar(pointListEntry.getValue());
    }

    private Predicate<CarDto> isCarNotRemovedFromGameMap(){
        return car -> Optional.ofNullable(car).map(CarDto::getPosition).isPresent();
    }
}

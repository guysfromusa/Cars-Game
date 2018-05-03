package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Konrad Rys, 03.05.2018
 */
@Component
public class CollisionMonitor {

    public void execute(List<Car> cars){

        Map<Point, List<Car>> potentialCollisionPoints = cars.stream().filter(isCarNotRemovedFromGameMap()).collect(Collectors.groupingBy(o -> o.getPosition()));

        potentialCollisionPoints.entrySet().stream().filter(isCollisionPoint()).forEach(this::resolveSinglePointCollision);
    }

    private void resolveSinglePointCollision(Map.Entry<Point, List<Car>> pointWithCars){
        List<Car> carOnCollision = pointWithCars.getValue();
        Map<Integer, List<Car>> carsByWeightRatio = carOnCollision.stream().collect(Collectors.groupingBy(o -> o.getType().getWeightRatio()));

        Optional<Integer> maxWeightRatioCar = carsByWeightRatio.keySet().stream().max(Comparator.naturalOrder());
        if(!maxWeightRatioCar.isPresent()){
            return;
        }
        Integer biggestCarWeightRatio = maxWeightRatioCar.get();
        markCrashedAllSmallerCars(biggestCarWeightRatio, carsByWeightRatio);
        markTrucksCrashedIfMoreThanOne(carsByWeightRatio.get(biggestCarWeightRatio));
    }

    private void markTrucksCrashedIfMoreThanOne(List<Car> cars) {
        if(isMoreThanOneTruck(cars)){
            markCarsCrashed(cars);
        }
    }

    private boolean isMoreThanOneTruck(List<Car> cars) {
        return cars.size() > 1;
    }

    private void markCrashedAllSmallerCars(Integer biggestCarWeightRatio, Map<Integer, List<Car>> carsByWeightRatio) {
        carsByWeightRatio.entrySet().stream()
                .filter(weightCarEntryList -> weightCarEntryList.getKey() != biggestCarWeightRatio)
                .map(Map.Entry::getValue)
                .forEach(this::markCarsCrashed);
    }

    private void markCarsCrashed(List<Car> car){
        car.stream().forEach(this::markAsCrashedFromMap);
    }

    private void markAsCrashedFromMap(Car car) {
        car.setCrashed(true);
        car.setPosition(null);
    }

    private Predicate<Map.Entry<Point, List<Car>>> isCollisionPoint(){
        return pointListEntry -> CollectionUtils.isNotEmpty(pointListEntry.getValue()) && pointListEntry.getValue().size() > 1;
    }

    private Predicate<Car> isCarNotRemovedFromGameMap(){
        return car -> Optional.ofNullable(car).map(Car::getPosition).isPresent();
    }
}

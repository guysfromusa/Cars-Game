package com.guysfromusa.carsgame.model;

import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

import static java.util.Arrays.asList;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
public enum Direction {

    NORTH(noChangesCoordicateX(), moveNorth()), EAST(moveEast(), noChangesCoordicateY()),
    SOUTH(noChangesCoordicateX(), moveSouth()), WEST(moveWest(), noChangesCoordicateY());

    @Getter
    private BiFunction<Point, Integer, Integer> forwardXF;

    @Getter
    private BiFunction<Point, Integer, Integer> forwardYF;

    Direction(BiFunction<Point, Integer, Integer> forwardXF, BiFunction<Point, Integer, Integer> forwardYF) {
        this.forwardXF = forwardXF;
        this.forwardYF = forwardYF;
    }

    private static final List<Direction> orderedDirections = asList(NORTH, EAST, SOUTH, WEST, NORTH);

    public Direction turnRight() {
        int currentIndex = orderedDirections.indexOf(this);
        return orderedDirections.get(++currentIndex);
    }

    public Direction turnLeft() {
        int currentIndex = orderedDirections.lastIndexOf(this);
        return orderedDirections.get(--currentIndex);
    }

    private static BiFunction<Point, Integer, Integer> moveNorth() {
        return (point, steps) -> point.getY() + steps;
    }

    private static BiFunction<Point, Integer, Integer> moveEast() {
        return (point, steps) -> point.getX() + steps;
    }

    private static BiFunction<Point, Integer, Integer> moveWest() {
        return (point, steps) -> point.getX() - steps;
    }

    private static BiFunction<Point, Integer, Integer> moveSouth() {
        return (point, steps) -> point.getY() - steps;
    }

    private static BiFunction<Point, Integer, Integer> noChangesCoordicateX() {
        return (point, steps) -> point.getX();
    }

    private static BiFunction<Point, Integer, Integer> noChangesCoordicateY() {
        return (point, steps) -> point.getY();
    }

}

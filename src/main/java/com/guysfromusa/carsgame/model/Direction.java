package com.guysfromusa.carsgame.model;

import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
public enum Direction {

    NORTH(Point::getX, moveNorth()), EAST(moveEast(), Point::getY),
    SOUTH(Point::getX, moveSouth()), WEST(moveWest(), Point::getY);

    @Getter
    private Function<Point, Integer> forwardXF;

    @Getter
    private Function<Point, Integer> forwardYF;

    Direction(Function<Point, Integer> forwardXF, Function<Point, Integer> forwardYF) {
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

    private static Function<Point, Integer> moveNorth() {
        return point -> point.getY() + 1;
    }

    private static Function<Point, Integer> moveEast() {
        return point -> point.getX() + 1;
    }

    private static Function<Point, Integer> moveWest() {
        return point -> point.getX() - 1;
    }

    private static Function<Point, Integer> moveSouth() {
        return point -> point.getY() - 1;
    }

}

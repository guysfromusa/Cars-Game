package com.guysfromusa.carsgame.model;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
public enum Direction {

    NORTH, EAST, SOUTH, WEST;

    private static final List<Direction> orderedDirections = asList(NORTH, EAST, SOUTH, WEST, NORTH);

    public Direction turnRight() {
        int currentIndex = orderedDirections.indexOf(this);
        return orderedDirections.get(++currentIndex);
    }

    public Direction turnLeft() {
        int currentIndex = orderedDirections.lastIndexOf(this);
        return orderedDirections.get(--currentIndex);
    }

}

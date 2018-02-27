package com.guysfromusa.carsgame.model;

import org.junit.Test;

import static com.guysfromusa.carsgame.model.Direction.*;
import static org.junit.Assert.*;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public class DirectionTest {

    @Test
    public void shouldTurnLeft() {
        assertEquals(WEST, Direction.NORTH.turnLeft());
        assertEquals(NORTH, Direction.EAST.turnLeft());
        assertEquals(EAST, Direction.SOUTH.turnLeft());
        assertEquals(SOUTH, Direction.WEST.turnLeft());
    }

    @Test
    public void shouldTurnRight() {
        assertEquals(EAST, Direction.NORTH.turnRight());
        assertEquals(SOUTH, Direction.EAST.turnRight());
        assertEquals(WEST, Direction.SOUTH.turnRight());
        assertEquals(NORTH, Direction.WEST.turnRight());
    }
}
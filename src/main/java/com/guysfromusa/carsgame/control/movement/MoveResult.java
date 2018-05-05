package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

/**
 * Created by Sebastian Mikucki, 06.05.18
 */
@Builder
@Value
@ToString
public class MoveResult {

    private String carName;

    private Point newPosition;

    private Direction newDirection;

    private boolean wall;

}

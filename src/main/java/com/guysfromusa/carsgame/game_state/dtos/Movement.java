package com.guysfromusa.carsgame.game_state.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Dominik Zurek 01.05.2018
 */
@NoArgsConstructor
public class Movement {

    @Getter
    private Operation operation;

    @Getter
    private Integer forwardSteps;

    private Movement(Operation operation, Integer forwardSteps) {
        this.operation = operation;
        this.forwardSteps = forwardSteps;
    }

    public static Movement newMovement(Operation operation){
        return new Movement(operation, 1);
    }

    public static Movement newMovement(Operation operation, Integer forwardSteps){
        return new Movement(operation, forwardSteps);
    }

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }
}

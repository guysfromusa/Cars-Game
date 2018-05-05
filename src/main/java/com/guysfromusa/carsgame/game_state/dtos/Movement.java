package com.guysfromusa.carsgame.game_state.dtos;

import lombok.Getter;

public class Movement {

    @Getter
    private final Operation operation;

    @Getter
    private final Integer forwardSteps;

    private Movement(Operation operation, Integer forwardSteps) {
        this.operation = operation;
        this.forwardSteps = forwardSteps;
    }

    public static Movement newMovement(Operation operation, Integer forwardSteps){
        return new Movement(operation, forwardSteps);
    }

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }
}

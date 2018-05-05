package com.guysfromusa.carsgame.game_state.dtos;

import lombok.Getter;

/**
 * Created by Dominik Zurek 01.05.2018
 */

public class Movement {

    @Getter
    private final Operation operation;

    private Movement(Operation operation) {
        this.operation = operation;
    }

    public static Movement newMovement(Operation operation){
        return new Movement(operation);
    }

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }
}

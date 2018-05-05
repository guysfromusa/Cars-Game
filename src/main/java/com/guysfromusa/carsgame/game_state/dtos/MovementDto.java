package com.guysfromusa.carsgame.game_state.dtos;

import lombok.Getter;

/**
 * Created by Dominik Zurek 01.05.2018
 */

public class MovementDto {

    @Getter
    private final Operation operation;

    private MovementDto(Operation operation) {
        this.operation = operation;
    }

    public static MovementDto newMovementDto(Operation operation){
        return new MovementDto(operation);
    }

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }
}

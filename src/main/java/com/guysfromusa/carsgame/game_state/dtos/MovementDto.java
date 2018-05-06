package com.guysfromusa.carsgame.game_state.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Dominik Zurek 01.05.2018
 */
@NoArgsConstructor
@ToString
public class MovementDto {

    @Getter
    private Operation operation;

    @Getter
    private Integer forwardSteps;

    private MovementDto(Operation operation, Integer forwardSteps) {
        this.operation = operation;
        this.forwardSteps = forwardSteps;
    }

    public static MovementDto newMovementDto(Operation operation) {
        return new MovementDto(operation, 1);
    }

    public static MovementDto newMovementDto(Operation operation, Integer forwardSteps) {
        return new MovementDto(operation, forwardSteps);
    }

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }
}

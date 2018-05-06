package com.guysfromusa.carsgame.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

    @Getter @Setter
    private Operation operation;

    @Getter @Setter
    private Integer forwardSteps;

    public enum Operation {
        LEFT, RIGHT, FORWARD
    }

}
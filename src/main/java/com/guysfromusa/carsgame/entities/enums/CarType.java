package com.guysfromusa.carsgame.entities.enums;

import lombok.Getter;

public enum CarType {

    NORMAL(1, 1),
    MONSTER(1, 2),
    RACER(2, 1);

    @Getter
    private int stepsPerMove;

    @Getter
    private int weightRatio;

    CarType(int stepsPerMove, int weightRatio) {
        this.stepsPerMove = stepsPerMove;
        this.weightRatio = weightRatio;
    }

    public boolean isValidStepsPerMove(int forwardSteps) {
        return forwardSteps >= 0 && forwardSteps <= stepsPerMove;
    }

}

package com.guysfromusa.carsgame.entities.enums;

public enum CarType {

    NORMAL(1),
    MONSTER(1),
    RACER(2);

    private int stepsPerMove;

    CarType(Integer stepsPerMove) {
        this.stepsPerMove = stepsPerMove;
    }

    public Integer getStepsPerMove() {
        return stepsPerMove;
    }
}

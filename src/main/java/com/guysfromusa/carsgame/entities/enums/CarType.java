package com.guysfromusa.carsgame.entities.enums;

public enum CarType {

    NORMAL(1),
    MONSTER(1),
    RACER(2);

    private Integer maxStepsPerRequest;

    CarType(Integer maxStepsPerRequest) {
        this.maxStepsPerRequest = maxStepsPerRequest;
    }

    public Integer getMaxStepsPerRequest() {
        return maxStepsPerRequest;
    }
}

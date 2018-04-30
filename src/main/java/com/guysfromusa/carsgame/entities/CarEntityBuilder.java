package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.CarType;

public final class CarEntityBuilder {

    private String name;
    private Integer positionX;
    private Integer positionY;
    private CarType carType;
    private boolean crashed;

    private CarEntityBuilder() {
    }

    public static CarEntityBuilder aCarEntity() {
        return new CarEntityBuilder();
    }

    public CarEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }



    public CarEntityBuilder withPositionX(Integer positionX) {
        this.positionX = positionX;
        return this;
    }

    public CarEntityBuilder withPositionY(Integer positionY) {
        this.positionY = positionY;
        return this;
    }

    public CarEntityBuilder withCarType(CarType carType) {
        this.carType = carType;
        return this;
    }

    public CarEntityBuilder withCrashed(boolean crashed) {
        this.crashed = crashed;
        return this;
    }

    public CarEntity build() {
        CarEntity carEntity = new CarEntity();
        carEntity.setName(name);
        carEntity.setPositionX(positionX);
        carEntity.setPositionY(positionY);
        carEntity.setCarType(carType);
        carEntity.setCrashed(crashed);
        return carEntity;
    }
}

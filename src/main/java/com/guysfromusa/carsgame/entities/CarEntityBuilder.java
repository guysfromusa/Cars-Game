package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.CarType;

//TODO move to @Builder on CarEntity
public final class CarEntityBuilder {

    private String name;
    private Integer positionX;
    private Integer positionY;
    private CarType carType;
    private boolean crashed;
    private String gameName;

    private CarEntityBuilder() {
    }

    public static CarEntityBuilder aCarEntity() {
        return new CarEntityBuilder();
    }

    public CarEntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CarEntityBuilder gameName(String name){
        this.gameName = name;
        return this;
    }

    public CarEntityBuilder positionX(Integer positionX) {
        this.positionX = positionX;
        return this;
    }

    public CarEntityBuilder positionY(Integer positionY) {
        this.positionY = positionY;
        return this;
    }

    public CarEntityBuilder carType(CarType carType) {
        this.carType = carType;
        return this;
    }

    public CarEntityBuilder crashed(boolean crashed) {
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
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName(gameName);
        carEntity.setGame(gameEntity);
        return carEntity;
    }
}

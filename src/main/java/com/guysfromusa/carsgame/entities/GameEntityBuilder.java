package com.guysfromusa.carsgame.entities;

import java.util.Set;

public final class GameEntityBuilder {
    private String name;
    private Set<CarEntity> cars;
    private MapEntity map;

    private GameEntityBuilder() {
    }

    public static GameEntityBuilder aGameEntity() {
        return new GameEntityBuilder();
    }


    public GameEntityBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public GameEntityBuilder withCars(Set<CarEntity> cars) {
        this.cars = cars;
        return this;
    }

    public GameEntityBuilder withMap(MapEntity map) {
        this.map = map;
        return this;
    }

    public GameEntity build() {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName(name);
        gameEntity.setCars(cars);
        gameEntity.setMap(map);
        return gameEntity;
    }
}

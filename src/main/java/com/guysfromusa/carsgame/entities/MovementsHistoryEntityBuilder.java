package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.model.Direction;

public final class MovementsHistoryEntityBuilder {
    private GameEntity game;
    private CarEntity car;
    private Integer positionX;
    private Integer positionY;
    private Direction direction;

    private MovementsHistoryEntityBuilder() {
    }

    public static MovementsHistoryEntityBuilder aMovementsHistoryEntity() {
        return new MovementsHistoryEntityBuilder();
    }

    public MovementsHistoryEntityBuilder withGame(GameEntity game) {
        this.game = game;
        return this;
    }

    public MovementsHistoryEntityBuilder withCar(CarEntity car) {
        this.car = car;
        return this;
    }

    public MovementsHistoryEntityBuilder withPositionX(Integer positionX) {
        this.positionX = positionX;
        return this;
    }

    public MovementsHistoryEntityBuilder withPositionY(Integer positionY) {
        this.positionY = positionY;
        return this;
    }

    public MovementsHistoryEntityBuilder withDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public MovementsHistoryEntity build() {
        MovementsHistoryEntity movementsHistoryEntity = new MovementsHistoryEntity();
        movementsHistoryEntity.setGame(game);
        movementsHistoryEntity.setCar(car);
        movementsHistoryEntity.setPositionX(positionX);
        movementsHistoryEntity.setPositionY(positionY);
        movementsHistoryEntity.setDirection(direction);
        return movementsHistoryEntity;
    }
}

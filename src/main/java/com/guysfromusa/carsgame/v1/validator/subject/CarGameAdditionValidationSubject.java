package com.guysfromusa.carsgame.v1.validator.subject;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.model.Point;

public class CarGameAdditionValidationSubject {

    private final CarEntity carEntity;
    private final GameEntity gameEntity;
    private final Point startingPoint;


    public CarGameAdditionValidationSubject(CarEntity carEntity, GameEntity gameEntity, Point startingPoint) {
        this.carEntity = carEntity;
        this.gameEntity = gameEntity;
        this.startingPoint = startingPoint;
    }

    public CarEntity getCarEntity() {
        return carEntity;
    }

    public GameEntity getGameEntity() {
        return gameEntity;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }
}

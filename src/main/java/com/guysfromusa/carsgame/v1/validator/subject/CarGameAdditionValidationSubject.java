package com.guysfromusa.carsgame.v1.validator.subject;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Getter;

public class CarGameAdditionValidationSubject {

    @Getter
    private final CarEntity carEntity;
    @Getter
    private final GameEntity gameEntity;
    @Getter
    private final Point startingPoint;


    public CarGameAdditionValidationSubject(CarEntity carEntity, GameEntity gameEntity, Point startingPoint) {
        this.carEntity = carEntity;
        this.gameEntity = gameEntity;
        this.startingPoint = startingPoint;
    }
}

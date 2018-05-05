package com.guysfromusa.carsgame.validator.subject;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarGameAdditionValidationSubject {

    private CarEntity carEntity;
    private GameEntity gameEntity;
    private Point startingPoint;
    private GameState gameState;

}

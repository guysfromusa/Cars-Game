package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.v1.model.Car;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public interface MovementStrategy {

    Movement.Operation getType();

    boolean execute(Car car, Integer[][] mapContent);
}

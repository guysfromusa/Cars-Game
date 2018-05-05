package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public interface MovementStrategy {

    Movement.Operation getType();

    MoveResult execute(CarDto car, Integer[][] mapContent, Movement movement);
}

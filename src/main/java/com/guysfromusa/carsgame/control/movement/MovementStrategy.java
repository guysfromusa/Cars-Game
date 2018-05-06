package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public interface MovementStrategy {

    MovementDto.Operation getType();

    MoveResult execute(CarDto car, Integer[][] mapContent, MovementDto movement);
}

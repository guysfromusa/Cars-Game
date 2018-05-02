package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.Data;

@Data
public class MovementMessage extends Message{

    private String carName;

    private Movement movement;

}

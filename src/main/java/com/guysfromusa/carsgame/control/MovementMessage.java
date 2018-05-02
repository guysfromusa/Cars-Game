package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.v1.model.Movement;
import lombok.Data;

@Data
public class MovementMessage extends Message{

    private String carName;

    private Movement movement;

}

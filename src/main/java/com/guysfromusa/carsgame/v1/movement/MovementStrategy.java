package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.v1.model.Movement;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
public interface MovementStrategy {

    Movement.Type getType();

    void execute(String game, String carName, Movement movement);
}

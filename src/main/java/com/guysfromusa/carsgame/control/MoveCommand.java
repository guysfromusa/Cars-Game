package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.game_state.dtos.Movement.Operation;
import com.guysfromusa.carsgame.v1.model.Car;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class MoveCommand extends Command<List<Car>> {

    @Getter
    private Movement movement;

    @Builder
    public MoveCommand(String gameName, String carName, MessageType messageType, Movement movement) {
        super(gameName, carName, messageType);
    }

}

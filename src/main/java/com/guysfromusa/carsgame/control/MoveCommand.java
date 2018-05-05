package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class MoveCommand extends Command<List<CarEntity>> {

    public MoveCommand(String gameName, String carName, MessageType messageType) {
        super(gameName, carName, messageType);
    }

    @Getter @Setter
    private Movement movement;


}

package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.Getter;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class MoveCommand extends Command<List<CarDto>> {

    @Getter
    private final MovementDto movementDto;

    @Getter
    private final boolean isUndo;

    public MoveCommand(String gameName, String carName, MessageType messageType, MovementDto movementDto, boolean isUndo) {
        super(gameName, carName, messageType);
        this.movementDto = movementDto;
        this.isUndo = isUndo;
    }
}

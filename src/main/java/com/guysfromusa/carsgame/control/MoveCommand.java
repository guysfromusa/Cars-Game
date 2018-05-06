package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@ToString(callSuper = true)
public class MoveCommand extends Command<List<CarDto>> {

    @Getter
    private final MovementDto movementDto;

    @Getter
    private final boolean isUndo;

    @Builder
    //TODO messageType can by hardcoded or via getMessageType
    public MoveCommand(String gameName, String carName, MessageType messageType, MovementDto movementDto, boolean isUndo) {
        super(gameName, carName, messageType);
        this.movementDto = movementDto;
        this.isUndo = isUndo;
    }
}

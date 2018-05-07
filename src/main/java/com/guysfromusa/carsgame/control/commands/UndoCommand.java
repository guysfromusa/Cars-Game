package com.guysfromusa.carsgame.control.commands;

import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 07.05.18
 */
@ToString(callSuper = true)
public class UndoCommand extends Command<List<MovementDto>> {

    @Getter
    private final int numberOfStepBack;

    @Builder
    public UndoCommand(String gameName, String carName, MessageType messageType, int numberOfStepBack) {
        super(gameName, carName, messageType);
        this.numberOfStepBack = numberOfStepBack;
    }

}

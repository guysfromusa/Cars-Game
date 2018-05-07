package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.commands.Command;
import lombok.Builder;
import lombok.ToString;

/**
 * Created by Konrad Rys, 07.05.2018
 */
@ToString(callSuper = true)
public class LastMoveWachCommand extends Command<String> {

    @Builder
    public LastMoveWachCommand(String gameName, String carName, MessageType messageType) {
        super(gameName, carName, messageType);
    }
}

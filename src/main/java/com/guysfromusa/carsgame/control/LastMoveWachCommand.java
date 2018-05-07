package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.control.commands.Command;
import lombok.Builder;

/**
 * Created by Konrad Rys, 07.05.2018
 */
public class LastMoveWachCommand extends Command<String> {

    @Builder
    public LastMoveWachCommand(String gameName, String carName, MessageType messageType) {
        super(gameName, carName, messageType);
    }
}

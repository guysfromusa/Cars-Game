package com.guysfromusa.carsgame.control;

import lombok.Builder;

/**
 * Created by Konrad Rys, 07.05.2018
 */
public class LastMoveWachCommand extends Command{

    @Builder
    public LastMoveWachCommand(String gameName, String carName, MessageType messageType) {
        super(gameName, carName, messageType);
    }
}

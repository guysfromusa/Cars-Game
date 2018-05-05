package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@AllArgsConstructor
public class GameRound {
    @Getter
    private String gameName;

    @Getter
    private List<Command> commands;

    @Getter
    private MessageType messageType;
}

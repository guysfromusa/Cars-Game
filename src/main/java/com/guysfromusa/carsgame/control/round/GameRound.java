package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.commands.Command;
import lombok.Value;

import java.util.List;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@Value
public class GameRound {
    private String gameName;
    private List<Command> commands;
    private MessageType messageType;
}

package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.Command;

import java.util.Optional;
import java.util.Queue;

/**
 * Created by Robert Mycek, 2018-05-05
 */
public interface GameRoundSelector {
    GameRound selectCommand(Queue<Command> queue, String gameName);
}

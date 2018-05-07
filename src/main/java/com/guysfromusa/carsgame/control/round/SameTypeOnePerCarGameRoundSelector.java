package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.control.commands.Command;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@Component
public class SameTypeOnePerCarGameRoundSelector implements GameRoundSelector {

    @Override
    public GameRound selectCommand(Queue<Command> queue, String gameName) {
        Validate.notEmpty(queue);

        MessageType type = queue.peek().getMessageType();
        Set<String> cars = new HashSet<>();
        List<Command> commands = new ArrayList<>();

        for (Iterator<Command> it = queue.iterator(); it.hasNext(); ) {
            Command command = it.next();
            if (!cars.contains(command.getCarName()) && command.getMessageType() == type) {
                it.remove();
                commands.add(command);
                cars.add(command.getCarName());
            }
        }

        return new GameRound(gameName, commands, type);
    }

}

package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.MessageType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@Component
public class SameTypeOnePerCarGameRoundSelector implements GameRoundSelector {

    @Override
    public Optional<GameRound> selectCommand(Queue<Command> queue, String gameName) {
        if (queue.isEmpty()) {
            return Optional.empty();
        }

        GameRound gameRound = selectCommands(queue, queue.peek().getMessageType(), gameName);
        return Optional.of(gameRound);
    }

    private GameRound selectCommands(Queue<Command> queue, MessageType type, String gameName) {
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

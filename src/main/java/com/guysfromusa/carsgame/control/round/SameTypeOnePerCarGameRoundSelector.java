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

import static com.guysfromusa.carsgame.control.MessageType.STOP_GAME;
import static java.util.Objects.nonNull;

/**
 * Created by Robert Mycek, 2018-05-05
 */
@Component
public class SameTypeOnePerCarGameRoundSelector implements GameRoundSelector {

    @Override
    public GameRound selectCommand(Queue<Command> queue, String gameName) {
        Validate.notEmpty(queue);
        MessageType type = queue.peek().getMessageType();

        Set<String> uniqueRoundSelectors = new HashSet<>();
        List<Command> commands = new ArrayList<>();

        for (Iterator<Command> it = queue.iterator(); it.hasNext(); ) {
            Command command = it.next();

            boolean isThisCarCommandNotAddedToRound = nonNull(command.getCarName())
                    && !uniqueRoundSelectors.contains(command.getCarName())
                    && command.getMessageType() == type;

            boolean isStopWatchCommandNotAdded = !uniqueRoundSelectors.contains(STOP_GAME.toString())
                    && command.getMessageType() == STOP_GAME;

            if (isThisCarCommandNotAddedToRound || isStopWatchCommandNotAdded) {
                it.remove();
                commands.add(command);
                if(nonNull(command.getCarName())){
                    uniqueRoundSelectors.add(command.getCarName());
                }else{
                    uniqueRoundSelectors.add(type.toString());
                }
            }
        }

        return new GameRound(gameName, commands, type);
    }

}

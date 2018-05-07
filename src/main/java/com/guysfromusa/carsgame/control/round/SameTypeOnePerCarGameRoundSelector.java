package com.guysfromusa.carsgame.control.round;

import com.guysfromusa.carsgame.control.Command;
import com.guysfromusa.carsgame.control.MessageType;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.guysfromusa.carsgame.control.MessageType.STOP_GAME;

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

            boolean isThisCarCommandNotAddedToRound = Objects.nonNull(command.getCarName())
                    && !uniqueRoundSelectors.contains(command.getCarName())
                    && command.getMessageType() == type;

            boolean isStopWatchCommandNotAdded = !uniqueRoundSelectors.contains(STOP_GAME.toString())
                    && command.getMessageType() == STOP_GAME;

            if (isThisCarCommandNotAddedToRound || isStopWatchCommandNotAdded) {
                it.remove();
                commands.add(command);
                if(Objects.nonNull(command.getCarName())){
                    uniqueRoundSelectors.add(command.getCarName());
                }else{
                    uniqueRoundSelectors.add(type.toString());
                }
            }
        }

        return new GameRound(gameName, commands, type);
    }

}

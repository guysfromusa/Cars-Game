package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.v1.model.Car;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@ToString(callSuper = true)
public class MoveCommand extends Command<List<Car>> {

    @Getter
    private Movement movement;

    @Builder
    //TODO messageType can by hardcoded or via getMessageType
    public MoveCommand(String gameName, String carName, MessageType messageType, Movement movement) {
        super(gameName, carName, messageType);
        this.movement = movement;
    }
}

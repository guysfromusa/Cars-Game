package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.entities.CarEntity;

import java.util.List;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
public class MoveCommand extends Command<List<CarEntity>> {

    public MoveCommand(String gameName, String carName, MessageType messageType) {
        super(gameName, carName, messageType);
    }

    //TODO add fields for move command

}

package com.guysfromusa.carsgame.control.commands;

import com.guysfromusa.carsgame.control.MessageType;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@ToString(callSuper = true)
public class AddCarToGameCommand extends Command<CarEntity> {

    @Getter
    private Point startingPoint;

    @Builder
    //TODO messageType can by hardcoded or via getMessageType
    private AddCarToGameCommand(String gameName, String carName, MessageType messageType, Point startingPoint) {
        super(gameName, carName, messageType);
        this.startingPoint = startingPoint;
    }
}

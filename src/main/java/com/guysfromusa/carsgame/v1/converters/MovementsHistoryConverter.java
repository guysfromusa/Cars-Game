package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MovementsHistoryConverter implements Converter<MovementsHistoryEntity, MovementHistory> {

    @Override
    public MovementHistory convert(MovementsHistoryEntity entity) {
        MovementHistory movementHistory = new MovementHistory();
        movementHistory.setCarName(entity.getCar().getName());
        movementHistory.setGameName(entity.getGame().getName());
        movementHistory.setPosition(new Point(entity.getPositionX(), entity.getPositionY()));
        movementHistory.setDirection(entity.getDirection());
        return movementHistory;
    }

}

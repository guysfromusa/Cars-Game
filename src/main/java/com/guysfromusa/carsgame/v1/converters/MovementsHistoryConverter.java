package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.google.common.collect.Lists.transform;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class MovementsHistoryConverter {

    public static MovementHistory toMovementHistory(MovementsHistoryEntity entity){
        MovementHistory movementHistory = new MovementHistory();
        movementHistory.setCarName(entity.getCar().getName());
        movementHistory.setGameName(entity.getGame().getName());
        movementHistory.setPosition(new Point(entity.getPositionX(), entity.getPositionY()));
        movementHistory.setDirection(entity.getDirection());

        return movementHistory;
    }

    public static List<MovementHistory> toMovementHistories(List<MovementsHistoryEntity> entities){
        return transform(entities, MovementsHistoryConverter::toMovementHistory);
    }
}

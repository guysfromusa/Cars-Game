package com.guysfromusa.carsgame.game_state.events;

import com.guysfromusa.carsgame.v1.model.Point;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.springframework.context.ApplicationEvent;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AddCarToGameEvent extends ApplicationEvent {

    private final String gameName;
    private final String carName;
    private final Point point;

    public AddCarToGameEvent(Object source, String gameName, String carName, Point point) {
        super(source);
        this.gameName = gameName;
        this.carName = carName;
        this.point = point;
    }
}

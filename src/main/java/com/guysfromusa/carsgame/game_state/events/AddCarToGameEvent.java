package com.guysfromusa.carsgame.game_state.events;

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

    public AddCarToGameEvent(Object source, String gameName, String carName) {
        super(source);
        this.gameName = gameName;
        this.carName = carName;
    }
}

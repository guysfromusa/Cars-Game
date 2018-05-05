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
public class AddNewGameEvent extends ApplicationEvent {

    private final String gameName;

    public AddNewGameEvent(Object source, String gameName) {
        super(source);
        this.gameName = gameName;
    }
}

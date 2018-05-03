package com.guysfromusa.carsgame.events;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Sebastian Mikucki, 03.05.18
 */
public class CommandEvent extends ApplicationEvent {

    public CommandEvent(Object source) {
        super(source);
    }
}

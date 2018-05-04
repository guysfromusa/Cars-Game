package com.guysfromusa.carsgame.control;

import org.springframework.context.ApplicationEvent;

/**
 * Created by Sebastian Mikucki, 03.05.18
 */
class CommandEvent extends ApplicationEvent {

    CommandEvent(Object source) {
        super(source);
    }
}

package com.guysfromusa.carsgame.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 03.05.18
 */
@Component
public class CommandEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Inject
    public CommandEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = notNull(applicationEventPublisher);
    }

    public void fire(Object source){
        CommandEvent commandEvent = new CommandEvent(source);
        applicationEventPublisher.publishEvent(commandEvent);
    }

}

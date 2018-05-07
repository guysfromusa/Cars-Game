package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.CommandProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Konrad Rys, 07.05.2018
 */

@Component
public class StopGameTask {

    private final CommandProducer commandProducer;

    @Inject
    public StopGameTask(CommandProducer commandProducer) {
        this.commandProducer = notNull(commandProducer);
    }

    @Scheduled(fixedRate = 1000)
    public void doGameStateCheck(){
        this.commandProducer.scheduleCommand()//TODO
    }
}

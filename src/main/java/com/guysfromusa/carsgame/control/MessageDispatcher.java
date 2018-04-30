package com.guysfromusa.carsgame.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class MessageDispatcher implements Runnable {

    @Value("${dispatcher.consume.delayInMs}")
    private long consumeDelay;

    private final GameQueue gameQueue;

    private final TaskExecutor taskExecutor;

    @Autowired
    public MessageDispatcher(GameQueue gameQueue, TaskExecutor taskExecutor) {
        this.gameQueue = notNull(gameQueue);
        this.taskExecutor = notNull(taskExecutor);
    }

    @PostConstruct
    public void init() {
        taskExecutor.execute(this);
    }

    @Override
    public void run() {
        while (true) {
            log.info("Consume all messages from queue");
            try {
                TimeUnit.MILLISECONDS.sleep(consumeDelay);
                List<Message> consumedMessages = new ArrayList<>();
                gameQueue.drainTo(consumedMessages);

                consumedMessages.forEach(message -> log.info("{}", message));
            } catch (InterruptedException e) {
                //TODO error handling
                log.warn(e.getMessage(), e);
            }
        }
    }
}

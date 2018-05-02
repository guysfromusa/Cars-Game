package com.guysfromusa.carsgame.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Component
@Slf4j
public class MessageDispatcher implements Runnable {

    private final GameQueue gameQueue;

    private final GameEngine gameEngine;

    private final TaskExecutor taskExecutor;

    @Autowired
    public MessageDispatcher(GameQueue gameQueue, GameEngine gameEngine, TaskExecutor taskExecutor) {
        this.gameQueue = notNull(gameQueue);
        this.gameEngine = notNull(gameEngine);
        this.taskExecutor = notNull(taskExecutor);
    }

    @PostConstruct
    public void init() {
        taskExecutor.execute(this);
    }

    @Override
    public void run() {
        while (true) {
            log.info("Consume messages from queue");

            //TODO temporary solution, refactor me
            //TODO filter messages by type and game and forward to engine
            Message firstMessage = null;
            try {
                firstMessage = gameQueue.take();
                log.info("Consumed first message");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Message> consumedMessages = new ArrayList<>(singletonList(firstMessage));
            log.info("DrainTo for messages");
            gameQueue.drainTo(consumedMessages);


            Map<MessageType, List<Message>> byType = consumedMessages.stream()
                    .collect(groupingBy(Message::getMessageType));

            byType.forEach(this::handle);
        }
    }

    private void handle(MessageType messageType, List<Message> messages) {
        //TODO Strategy someday
        switch (messageType) {
            case MOVE:
                gameEngine.handleMoves(messages);
                break;
            default:
                throw new IllegalStateException("Undefined message type");
        }
    }
}

package com.guysfromusa.carsgame.control;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static java.time.LocalDateTime.now;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Data
public class Message implements Comparable<Message> {

    private final CompletableFuture<String> future = new CompletableFuture<>();
    private LocalDateTime dateTime = now();
    private String gameName;
    private MessageType messageType;

    @Override
    public int compareTo(Message o) {
        return this.dateTime.compareTo(o.getDateTime());
    }

    CompletableFuture<String> getFuture() {
        return future;
    }
}

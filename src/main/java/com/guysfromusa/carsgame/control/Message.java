package com.guysfromusa.carsgame.control;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Data
public class Message {

    //TODO type String to some other representation of finish
    private final CompletableFuture<String> future = new CompletableFuture<>();

    private String gameName;

    private MessageType messageType;
    //TODO add other fields representing game internal message

    public CompletableFuture<String> getFuture() {
        return future;
    }
}

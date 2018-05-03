package com.guysfromusa.carsgame.control;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@Data
//<T> each command can return different type from future
public abstract class Command<T> {

    //TODO type String to some other representation of finish
    private final CompletableFuture<T> future = new CompletableFuture<>();

    private String gameName;

    private String carName;

    private MessageType messageType;
    //TODO add other fields representing game internal message

    public CompletableFuture<T> getFuture() {
        return future;
    }
}

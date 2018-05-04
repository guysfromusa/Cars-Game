package com.guysfromusa.carsgame.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
//<T> each command can return different type from future
@AllArgsConstructor
public abstract class Command<T> {

    @Getter
    private final CompletableFuture<T> future = new CompletableFuture<>();

    @Getter
    private String gameName;

    @Getter
    private String carName;

    @Getter
    private MessageType messageType;

}
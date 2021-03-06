package com.guysfromusa.carsgame.control.commands;

import com.guysfromusa.carsgame.control.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Sebastian Mikucki, 30.04.18
 * @param <T> type returned by future
 */
@AllArgsConstructor
@ToString
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

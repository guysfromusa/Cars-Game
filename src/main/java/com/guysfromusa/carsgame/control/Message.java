package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
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
    private String carName;
    private MessageType messageType;
    private Movement movement;
    //TODO add other fields representing game internal message

    public CompletableFuture<String> getFuture() {
        return future;
    }
}

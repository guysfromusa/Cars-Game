package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Message;

import java.util.Arrays;

public final class GameStateBuilder {

    private volatile boolean roundInProgress = false;

    private String gameName;

    private Message[] messages;

    private GameStateBuilder() {
    }

    public static GameStateBuilder aGameState() {
        return new GameStateBuilder();
    }

    public GameStateBuilder roundInProgress(boolean roundInProgress) {
        this.roundInProgress = roundInProgress;
        return this;
    }

    public GameStateBuilder gameName(String gameName) {
        this.gameName = gameName;
        return this;
    }

    public GameStateBuilder movementsQueue(Message ... messages) {
        this.messages = messages;
        return this;
    }

    public GameState build() {
        GameState gameState = new GameState(gameName);
        gameState.setRoundInProgress(roundInProgress);
        Arrays.stream(messages)
                .forEach(gameState.getCommandsQueue()::add);
        return gameState;
    }
}

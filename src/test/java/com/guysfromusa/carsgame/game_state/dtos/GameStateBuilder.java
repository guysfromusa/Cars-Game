package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Command;

import java.util.Arrays;

public final class GameStateBuilder {

    private volatile boolean roundInProgress = false;

    private String gameName;

    private Command[] commands;

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

    public GameStateBuilder movementsQueue(Command... commands) {
        this.commands = commands;
        return this;
    }

    public GameState build() {
        GameState gameState = new GameState(gameName, new Integer[0][0]);
        gameState.setRoundInProgress(roundInProgress);
        Arrays.stream(commands)
                .forEach(gameState.getCommandsQueue()::add);
        return gameState;
    }
}

package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.v1.model.Car;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@AllArgsConstructor
public class MoveData {
    @Getter
    private GameState gameState;
    @Getter
    private MoveCommand moveCommand;

    public Car getCar(){
        String carName = moveCommand.getCarName();
        return gameState.getCar(carName);
    }

    public List<Car> getCars(){
        return gameState.getCarsInGame();
    }

    public CompletableFuture<List<Car>> getFuture(){
        return moveCommand.getFuture();
    }
}

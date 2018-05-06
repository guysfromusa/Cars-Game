package com.guysfromusa.carsgame.control;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
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

    public CarDto getCar(){
        String carName = moveCommand.getCarName();
        return gameState.getCar(carName);
    }

    public int getForwardSteps(){
        return moveCommand.getMovementDto().getForwardSteps();
    }

    public List<CarDto> getCars(){
        return gameState.getAllCars();
    }

    public CompletableFuture<List<CarDto>> getFuture(){
        return moveCommand.getFuture();
    }
}

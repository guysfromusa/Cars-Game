package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.control.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Slf4j
public class GameState {

    @Getter
    private final String gameName;

    @Getter
    private final BlockingQueue<Message> movementsQueue = new LinkedBlockingQueue<>(); //TODO sync queue

    @Setter @Getter
    private volatile boolean roundInProgress = false;

    private Map<String, List<Movement>> movementsHistoryByCar = new HashMap<>(); //TODO sychronize

    public GameState(String gameName) {
        this.gameName = gameName;
    }

    public CompletableFuture<String> addMovementToExecute(Message message) {
        boolean added = movementsQueue.offer(message);
        return added ? message.getFuture() : completedFuture("{error:queue is full}");
    }

    public void addMovementHistory(String carName, Movement.Operation operation) {
        List<Movement> carsMovement = movementsHistoryByCar.get(carName);
        carsMovement.add(Movement.newMovement(operation));
    }

    //FIXME why do we need this maybe getOrDefault()?
    public void addNewCar(String carName) {
        movementsHistoryByCar.put(carName, new ArrayList<>());
    }

    public List<Movement> getMovementHistory(String carName) {
        return movementsHistoryByCar.get(carName);
    }
}

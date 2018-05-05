package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.exceptions.ApiError;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.MovementResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Konrad Rys, 05.05.2018
 */

@Component
public class MovementResultService {

    public static final String UNEXPECTED_MESSAGE = "Unexpected message occured while taking movement";

    public static final String QUEUE_OVERLOADED = "Movement queue overloaded";

    public MovementResult success(List<Car> cars) {
        MovementResult movementResult = new MovementResult();
        movementResult.setCars(cars);
        return movementResult;
    }

    public MovementResult failure(List<Car> cars, String message) {
        ApiError failure = ApiError.builder()
                .date(LocalDateTime.now())
                .message(message)
                .status("FAILURE")
                .build();

        MovementResult movementResult = new MovementResult();
        movementResult.setApiError(failure);
        movementResult.setCars(cars);
        return movementResult;
    }

    public MovementResult failure(String message){
        return failure(null, message);
    }
}

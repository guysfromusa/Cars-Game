package com.guysfromusa.carsgame.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@AllArgsConstructor
public enum MoveStatus {

    SUCCESS("Ok"),
    ALREADY_CRASHED("Move cannot be made as car is already crashed"),
    CRASHED_INDO_WALL("Car was crashed into wall"),
    CRASHED_WITH_OTHER("Car was crashed with other");

    @Getter
    private String message;
}

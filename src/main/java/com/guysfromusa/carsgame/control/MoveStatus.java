package com.guysfromusa.carsgame.control;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Konrad Rys, 05.05.2018
 */
@AllArgsConstructor
public enum MoveStatus {

    SUCCESS("Ok", true),
    ALREADY_CRASHED("Move cannot be made as car is already crashed", false),
    CRASHED_INDO_WALL("Car was crashed into wall", false),
    CRASHED_WITH_OTHER("Car was crashed with other", false);

    @Getter
    private String message;

    @Getter
    private boolean moved;
}

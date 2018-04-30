package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.entities.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@NoArgsConstructor
@AllArgsConstructor
public class GameStatusDto {

    @Setter
    @Getter
    private GameStatus status;

}

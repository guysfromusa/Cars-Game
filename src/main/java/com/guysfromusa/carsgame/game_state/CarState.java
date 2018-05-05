package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Konrad Rys, 04.05.2018
 */
public class CarState {

    @Getter
    private Collection<Movement> movements = new ConcurrentLinkedQueue<>();

    @Getter @Setter
    private CarDto car;

}

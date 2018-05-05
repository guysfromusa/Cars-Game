package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.v1.model.Car;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Konrad Rys, 04.05.2018
 */
public class CarState {

    @Getter
    private Collection<MovementDto> movementDtos = new ConcurrentLinkedQueue<>();

    @Getter @Setter
    private Car car;


}

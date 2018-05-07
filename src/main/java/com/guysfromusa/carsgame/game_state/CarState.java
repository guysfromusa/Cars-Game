package com.guysfromusa.carsgame.game_state;


import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Konrad Rys, 04.05.2018
 */
@ToString
public class CarState {

    @Getter
    private Collection<MovementDto> movementDtos = new ConcurrentLinkedQueue<>();

    @Getter @Setter
    private CarDto car;

}

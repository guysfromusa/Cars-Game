package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation;
import com.guysfromusa.carsgame.v1.model.Movement;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;

/**
 * Created by Sebastian Mikucki, 06.05.18
 */
@Component
public class MovementConverter implements Converter<Movement, MovementDto> {

    @Override
    public MovementDto convert(Movement source) {
        return newMovementDto(Operation.valueOf(source.getOperation().name()),
                source.getForwardSteps());
    }

}

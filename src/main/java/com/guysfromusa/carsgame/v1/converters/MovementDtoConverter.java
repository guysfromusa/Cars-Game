package com.guysfromusa.carsgame.v1.converters;

import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.v1.model.UndoNStepPath;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MovementDtoConverter implements Converter<MovementDto, UndoNStepPath>{

    @Override
    public UndoNStepPath convert(MovementDto movementDto){
        return UndoNStepPath.builder()
                .operation(movementDto.getOperation().name())
                .build();
    }
}

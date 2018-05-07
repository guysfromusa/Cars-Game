package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.control.CommandProducer;
import com.guysfromusa.carsgame.control.commands.UndoCommand;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.v1.model.UndoNStepPath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.control.MessageType.UNDO;
import static com.guysfromusa.carsgame.utils.StreamUtils.convert;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Dominik Zurek 02.05.2018
 */
@RestController
@RequestMapping(value = "/v1/back-movements", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "back-movements", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class UndoMovementResource {

    private final CommandProducer commandProducer;
    private final ConversionService conversionService;

    @Inject
    public UndoMovementResource(CommandProducer commandProducer, ConversionService conversionService) {
        this.commandProducer = notNull(commandProducer);
        this.conversionService = notNull(conversionService);
    }


    @ApiOperation(value = "Find history", response = List.class)
    @GetMapping("/{gameId}/{carName}/{numberOfStepsBack}")
    public List<UndoNStepPath> findMovementHistory(@PathVariable("gameId") String gameId,
                                                   @PathVariable("carName") String carName,
                                                   @PathVariable("numberOfStepsBack") int numberOfStepsBack) {
        log.info("Handle undo operation: game: {}, car: {}, steps: {}", gameId, carName, numberOfStepsBack);
        UndoCommand undoCommand = UndoCommand.builder()
                .gameName(gameId)
                .carName(carName)
                .numberOfStepBack(numberOfStepsBack)
                .messageType(UNDO)
                .build();

        List<MovementDto> movements = commandProducer.scheduleCommand(undoCommand);
        return convert(movements, movementDto -> conversionService.convert(movementDto, UndoNStepPath.class));
    }

}

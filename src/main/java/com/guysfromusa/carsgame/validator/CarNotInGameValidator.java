package com.guysfromusa.carsgame.validator;

import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

import static com.guysfromusa.carsgame.utils.StreamUtils.convert;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@Slf4j
@Component
public class CarNotInGameValidator implements BusinessValidator<CarGameAdditionValidationSubject> {

    public static final String CAR_EXISTS_IN_GAME_MESSAGE = "Car is already added to game";

    private final ActiveGamesContainer activeGamesContainer;

    @Inject
    public CarNotInGameValidator(ActiveGamesContainer activeGamesContainer) {
        this.activeGamesContainer = notNull(activeGamesContainer);
    }

    @Override
    public void validate(CarGameAdditionValidationSubject subject) {
        GameState gameState = activeGamesContainer.getGameState(subject.getGameEntity().getName());
        List<String> gamesCarNames = convert(gameState.getAllCars(), CarDto::getName);
        String carName = subject.getCarEntity().getName();
        if(gamesCarNames.contains(carName)){
            throw new IllegalArgumentException(CAR_EXISTS_IN_GAME_MESSAGE);
        }
    }
}

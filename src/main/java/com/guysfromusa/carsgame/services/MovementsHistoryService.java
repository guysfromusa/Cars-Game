package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.control.movement.MoveResult;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.utils.StreamUtils;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.guysfromusa.carsgame.entities.MovementsHistoryEntityBuilder.aMovementsHistoryEntity;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Service
@Transactional
public class MovementsHistoryService {

    private final CarRepository carRepository;
    private final GameRepository gameRepository;
    private final MovementsHistoryRepository repository;
    private final ConversionService conversionService;

    @Autowired
    public MovementsHistoryService(CarRepository carRepository,
                                   GameRepository gameRepository,
                                   MovementsHistoryRepository repository,
                                   ConversionService conversionService) {
        this.carRepository = notNull(carRepository);
        this.gameRepository = notNull(gameRepository);
        this.repository = notNull(repository);
        this.conversionService = notNull(conversionService);
    }

    @Transactional(readOnly = true)
    public List<MovementHistory> findMovementsHistory(List<String> gameIds, List<String> carNames, Optional<Integer> limitOfRecentStep) {
        List<MovementsHistoryEntity> movements = repository.findMovements(gameIds, carNames, limitOfRecentStep);
        return StreamUtils.convert(movements,
                movementsHistoryEntity -> conversionService.convert(movementsHistoryEntity, MovementHistory.class));
    }

    public void saveMove(String gameName, MoveResult moveResult) {
        CarEntity carEntity = carRepository.findByGameAndName(gameName, moveResult.getCarName())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find car"));

        GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find game"));

        MovementsHistoryEntity movementsHistoryEntity = aMovementsHistoryEntity()
                .car(carEntity)
                .game(gameEntity)
                .direction(moveResult.getNewDirection())
                .positionX(moveResult.getNewPosition().getX())
                .positionY(moveResult.getNewPosition().getY())
                .build();

        repository.save(movementsHistoryEntity);
    }
}

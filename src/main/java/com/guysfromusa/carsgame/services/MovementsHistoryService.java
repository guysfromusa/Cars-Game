package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.utils.StreamUtils;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Service
@Transactional(readOnly = true)
public class MovementsHistoryService {

    private final MovementsHistoryRepository repository;
    private final ConversionService conversionService;

    @Inject
    public MovementsHistoryService(MovementsHistoryRepository repository,  ConversionService conversionService) {
        this.repository = notNull(repository);
        this.conversionService = notNull(conversionService);
    }

    public List<MovementHistory> findMovementsHistory(List<String> gameIds, List<String> carNames, Optional<Integer> limitOfRecentStep) {
        List<MovementsHistoryEntity> movements = repository.findMovements(gameIds, carNames, limitOfRecentStep);
        return StreamUtils.convert(movements,
                movementsHistoryEntity -> conversionService.convert(movementsHistoryEntity, MovementHistory.class));
    }
}

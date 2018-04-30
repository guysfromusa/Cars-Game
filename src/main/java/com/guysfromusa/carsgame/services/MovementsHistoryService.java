package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.converters.MovementsHistoryConverter;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public MovementsHistoryService(MovementsHistoryRepository repository) {
        this.repository = notNull(repository);
    }

    public List<MovementHistory> findMovementsHistory(Optional<List<String>> gameIds, Optional<List<String>> carNames, Optional<Integer> limitOfRecentStep) {
        List<MovementsHistoryEntity> movements = repository.findMovements(gameIds, carNames, limitOfRecentStep);
        return MovementsHistoryConverter.toMovementHistories(movements);
    }
}

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

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Service
@Transactional(readOnly = true)
public class MovementsHistoryService {

    private final MovementsHistoryRepository repository;
    private final MovementsHistoryConverter converter;

    @Autowired
    public MovementsHistoryService(MovementsHistoryRepository repository, MovementsHistoryConverter converter) {
        this.repository = notNull(repository);
        this.converter = notNull(converter);
    }

    public List<MovementHistory> findMovementsHistory(List<String> gameIds, List<String> carNames, Optional<Integer> limitOfRecentStep) {
        List<MovementsHistoryEntity> movements = repository.findMovements(gameIds, carNames, limitOfRecentStep);
        return movements.stream().map(converter::convert).collect(toList());
    }
}

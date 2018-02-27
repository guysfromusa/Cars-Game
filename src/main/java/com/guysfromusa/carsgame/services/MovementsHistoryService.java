package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Service
public class MovementsHistoryService {

    private final MovementsHistoryRepository repository;

    @Autowired
    public MovementsHistoryService(MovementsHistoryRepository repository) {
        this.repository = notNull(repository);
    }

    public List<MovementHistory> findMovementsHistory(String gameId, String carName, int limitOfRecentStep){

        List<MovementsHistoryEntity> result = repository.findMovements(gameId, carName, limitOfRecentStep);
        return Collections.emptyList();
    }
}

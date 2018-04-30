package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;

import java.util.List;
import java.util.Optional;

public interface MovementsHistoryRepositoryCustom {

    List<MovementsHistoryEntity> findMovements(Optional<List<String>> gameIds, Optional<List<String>> carNames, Optional<Integer> limitOfRecentStep);
}

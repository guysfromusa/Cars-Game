package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;

import java.util.List;

public interface MovementsHistoryRepositoryCustom {

    List<MovementsHistoryEntity> findMovements(List<String> gameIds, List<String> carNames, int limitOfRecentStep);
}

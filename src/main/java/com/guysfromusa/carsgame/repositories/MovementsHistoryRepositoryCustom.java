package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;

import java.util.List;

public interface MovementsHistoryRepositoryCustom {

    List<MovementsHistoryEntity> findMovements(String gameId, String carName, int limitOfRecentStep);
}

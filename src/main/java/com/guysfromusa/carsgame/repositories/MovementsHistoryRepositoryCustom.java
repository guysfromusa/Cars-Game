package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistory;

import java.util.List;

public interface MovementsHistoryRepositoryCustom {

    List<MovementsHistory> findMovements(String gameId, String mapName, String carName, int limitOfRecentStep);
}

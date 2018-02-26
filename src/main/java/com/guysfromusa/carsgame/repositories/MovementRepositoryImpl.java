package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class MovementRepositoryImpl implements MovementHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MovementRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MovementsHistory> findMovements(String gameId, String mapName, String carName, int limitOfRecentStep){

        return Collections.emptyList();
    }

}

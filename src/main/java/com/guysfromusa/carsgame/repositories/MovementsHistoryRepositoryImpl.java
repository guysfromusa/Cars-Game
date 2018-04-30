package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class MovementsHistoryRepositoryImpl implements MovementsHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MovementsHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = notNull(entityManager);
    }

    @Override
    public List<MovementsHistoryEntity> findMovements(Optional<List<String>> gameIds, Optional<List<String>> carNames, Optional<Integer> limitOfRecentStep){

        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(MovementsHistoryEntity.class, "movementHistory");
        criteria.createAlias("movementHistory.car", "car");
        criteria.createAlias("movementHistory.game", "game");

        //TODO consider projection to avoid eagerly loaded entities
        if(!carNames.isPresent()) criteria.add(Restrictions.in("car.name", carNames.get()));
        if(!gameIds.isPresent()) criteria.add(Restrictions.in("game.name", gameIds.get()));

        if(limitOfRecentStep.isPresent()) {
            criteria.addOrder(Order.desc("createDateTime"));
            criteria.setMaxResults(limitOfRecentStep.get());
        }

        return criteria.list();
    }
}

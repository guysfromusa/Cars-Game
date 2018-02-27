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

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class MovementsHistoryRepositoryImpl implements MovementsHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MovementsHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = notNull(entityManager);
    }

    @Override
    public List<MovementsHistoryEntity> findMovements(List<String> gameNames, List<String> carNames, int limitOfRecentStep){

        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(MovementsHistoryEntity.class, "movementHistory");
        criteria.createAlias("movementHistory.car", "car");
        criteria.createAlias("movementHistory.game", "game");

        if(!carNames.isEmpty()) criteria.add(Restrictions.in("car.name", carNames));
        if(!gameNames.isEmpty()) criteria.add(Restrictions.in("game.name", gameNames));

        if(limitOfRecentStep != 0) {
            criteria.addOrder(Order.desc("createDateTime"));
            criteria.setMaxResults(limitOfRecentStep);
        }

        return criteria.list();
    }
}

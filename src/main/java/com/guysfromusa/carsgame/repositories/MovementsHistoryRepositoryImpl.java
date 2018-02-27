package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Transactional(readOnly = true)
public class MovementsHistoryRepositoryImpl implements MovementsHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MovementsHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = notNull(entityManager);
    }

    @Override
    public List<MovementsHistoryEntity> findMovements(String gameId, String carName, int limitOfRecentStep){

        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(MovementsHistoryEntity.class, "movementHistory");
        criteria.createAlias("movementHistory.car", "car");
        criteria.createAlias("movementHistory.game", "game");

        if(!carName.isEmpty()) criteria.add(Restrictions.eq("car.name", carName));
       // if(!gameId.isEmpty()) criteria.add(Restrictions.eq("gameId", gameId));

        if(limitOfRecentStep != 0) {
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(limitOfRecentStep);
        }

        return criteria.list();
    }
}

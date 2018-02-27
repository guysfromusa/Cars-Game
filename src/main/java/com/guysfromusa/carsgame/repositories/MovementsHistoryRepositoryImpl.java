package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class MovementsHistoryRepositoryImpl implements MovementsHistoryRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public MovementsHistoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<MovementsHistory> findMovements(String gameId, String mapName, String carName, int limitOfRecentStep){

        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(MovementsHistory.class);
        //todo : create mcase interface :)
        if(!carName.isEmpty()) criteria.add(Restrictions.eq("carName", carName));
        if(!mapName.isEmpty()) criteria.add(Restrictions.eq("mapName", mapName));
        if(!gameId.isEmpty()) criteria.add(Restrictions.eq("gameId", gameId));

        if(limitOfRecentStep != 0) {
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(limitOfRecentStep);
        }

        return criteria.list();
    }
}

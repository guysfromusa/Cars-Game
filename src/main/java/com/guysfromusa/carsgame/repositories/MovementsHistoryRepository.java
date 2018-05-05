package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dominik Zurek, 26.02.18
 */
@Repository
public interface MovementsHistoryRepository extends JpaRepository<MovementsHistoryEntity, Long>, MovementsHistoryRepositoryCustom {

}

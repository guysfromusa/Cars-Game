package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MovementsHistory;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dominik Zurek, 26.02.18
 */

public interface MovementsHistoryRepository extends CrudRepository<MovementsHistory, Long>, MovementHistoryRepositoryCustom {

}

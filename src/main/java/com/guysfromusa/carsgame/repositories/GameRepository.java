package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public interface GameRepository extends CrudRepository<GameEntity, Long> {
}

package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Robert Mycek, 2018-02-27
 */
@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Optional<GameEntity> findByName(String name);

}

package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    Optional<CarEntity> findByName(String name);

    @Query("from CarEntity c " +
            "where c.name = :name " +
            "and c.game.name = :gameName")
    Optional<CarEntity> findByGameAndName(@Param("gameName") String game, @Param("name") String name);

    Long deleteByName(String name);

    @Query("from CarEntity c " +
            "where c.game.name = :gameName")
    List<CarEntity> findByGame(@Param("gameName") String game);

}

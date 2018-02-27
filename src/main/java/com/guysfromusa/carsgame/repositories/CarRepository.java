package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
@Repository
public interface CarRepository extends CrudRepository<CarEntity, Long> {

    CarEntity findByName(String name);

    @Query("from CarEntity c " +
            "where c.name = :name " +
            "and c.game.name = :gameName")
    CarEntity findByGameAndName(@Param("gameName") String game, @Param("name") String name);

    long deleteByName(String name);
}

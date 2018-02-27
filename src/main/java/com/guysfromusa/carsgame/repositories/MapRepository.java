package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity, Long> {

    Optional<MapEntity> findByNameAndDeleted(String name, boolean deleted);

    @Query("delete from MapEntity m where m.name = :name and deleted = true") //TODO: need to join with GameEntity
    boolean deleteByName(@Param("name") String name);
}

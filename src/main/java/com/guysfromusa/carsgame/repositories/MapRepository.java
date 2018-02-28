package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<MapEntity, Long> {

    Optional<MapEntity> findByNameAndDeleted(String name, boolean deleted);

    @Modifying
    @Query("delete from MapEntity m " +
            "where m.name = :name " +
            "and not exists (select g.id from GameEntity g where g.map.id = m.id)")
    void deleteByName(@Param("name") String name);
}

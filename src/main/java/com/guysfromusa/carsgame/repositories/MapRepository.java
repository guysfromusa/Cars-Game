package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<MapEntity, Long> {

    Optional<MapEntity> findByNameAndDeleted(String name, boolean deleted);

    @Modifying
    @Transactional
    @Query("delete from MapEntity m " +
            "where m.name = :name " +
            "and m.id not in (select g.map.id from GameEntity g)")
    void deleteByName(@Param("name") String name);
}

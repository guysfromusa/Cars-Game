package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MapRepository extends JpaRepository<MapEntity, Long> {

}

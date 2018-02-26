package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.CarEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long> {

    long deleteByName(String name);

}

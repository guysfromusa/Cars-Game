package com.guysfromusa.carsgame.repositories;

import com.guysfromusa.carsgame.entities.CarEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
public interface CarRepository extends CrudRepository<CarEntity, Long> {

    CarEntity findByName(String name);

}

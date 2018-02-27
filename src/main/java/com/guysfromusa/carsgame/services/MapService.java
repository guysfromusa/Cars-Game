package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.Map;
import com.guysfromusa.carsgame.model.TurnSide;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@Service
public class MapService {

    private final MapRepository mapRepository;

    @Inject
    public MapService(MapRepository mapRepository) {
        this.mapRepository = notNull(mapRepository);
    }

    public Iterable<Map> findAll() {
        return mapRepository.findAll();
    }


}

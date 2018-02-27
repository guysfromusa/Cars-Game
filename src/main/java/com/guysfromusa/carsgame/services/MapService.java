package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Sebastian Mikucki, 26.02.18
 */
@Service
@Transactional
public class MapService {

    private final MapRepository mapRepository;

    @Inject
    public MapService(MapRepository mapRepository) {
        this.mapRepository = notNull(mapRepository);
    }

    public List<MapEntity> findAll() {
        return mapRepository.findAll();
    }

    public MapEntity create(MapEntity map) {

        return mapRepository.save(map);
    }

    public Optional<MapEntity> markAsDeleted(String name) {
        Optional<MapEntity> map = mapRepository.findByNameAndDeleted(name, false);
        map.ifPresent(m -> m.setDeleted(true));
        return map;
    }

    public void delete(MapEntity map) {
        mapRepository.deleteByName(map.getName());
    }

    public MapEntity findById(Long id) {
        return mapRepository.findOne(id);
    }
}

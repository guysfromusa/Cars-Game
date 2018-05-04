package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.MapRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.guysfromusa.carsgame.entities.MapEntity.ACTIVE;
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

    public Optional<MapEntity> find(String name) {
        return mapRepository.findByNameAndActive(name, ACTIVE);
    }

    public void delete(String name) {
        mapRepository.deleteByName(name);
        mapRepository.findByNameAndActive(name, ACTIVE)
                .ifPresent(MapEntity::deactivate);
    }

    public MapEntity create(String name, String content) {
        return mapRepository.save(new MapEntity(name, content));
    }

    public boolean isFieldReachableOnGameMap(String content, Point point){
        Integer[][] mapContent = GameMapUtils.getMapMatrixContent(content);
        Integer x = point.getX();
        Integer y = point.getY();
        return isFieldReachableOnGameMap(mapContent, x, y);
    }

    public boolean isFieldReachableOnGameMap(Integer[][] content, Integer x, Integer y){
        return isInMapReach(content, x, y) && isOnRoad(content, x, y);
    }

    private boolean isOnRoad(Integer[][] mapContent, Integer x, Integer y) {

        return mapContent[y][x]== 1;
    }

    private boolean isInMapReach(Integer[][] mapContent, Integer x, Integer y) {
        return y >= 0
                && x >=0
                && mapContent.length > y
                && mapContent[mapContent.length-1].length > x;
    }


}

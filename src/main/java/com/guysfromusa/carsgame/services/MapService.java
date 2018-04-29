package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.GameMapUtils;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.MapRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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

    public void deleteByName(String name) {
        mapRepository.deleteByName(name);
        mapRepository.findByNameAndActive(name, ACTIVE)
                .ifPresent(MapEntity::deactivate);
    }

    public MapEntity create(String name, String content) {
        return mapRepository.save(new MapEntity(name, content));
    }

    public boolean isPositionValidOnGameMap(String content, Point point){
        Integer[][] mapContent = GameMapUtils.getMapMatrixFromContent(content);

        return isInMapReach(mapContent, point) && isOnRoad(mapContent, point);
    }

    private boolean isOnRoad(Integer[][] mapContent, Point point) {
        Integer x = point.getX();
        Integer y = point.getY();
        return mapContent[y][x]== 1;
    }

    private boolean isInMapReach(Integer[][] mapContent, Point point) {
        return point.getY() >= 0
                && point.getX() >=0
                && mapContent.length > point.getY()
                && mapContent[mapContent.length-1].length > point.getX();
    }


}

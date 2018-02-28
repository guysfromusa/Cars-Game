package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

import static com.guysfromusa.carsgame.entities.MapEntity.ACTIVE;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    private final MapRepository mapRepository;

    @Inject
    public GameService(GameRepository gameRepository, MapRepository mapRepository) {
        this.gameRepository = notNull(gameRepository);
        this.mapRepository = notNull(mapRepository);
    }

    @Transactional
    public GameEntity startNewGame(String gameName, String mapName) {
        Optional<MapEntity> mapOptional = mapRepository.findByNameAndActive(mapName, ACTIVE);

        //TODO negative response if game already exist

        MapEntity map = mapOptional
                .orElseThrow(() -> new IllegalArgumentException("Map '" + mapName + "' not found"));

        GameEntity game = new GameEntity();
        game.setName(gameName);
        game.setMap(map);
        return gameRepository.save(game);
    }
}

package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.entities.enums.GameStatus;
import com.guysfromusa.carsgame.exceptions.EntityNotFoundException;
import com.guysfromusa.carsgame.repositories.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    private final MapService mapService;

    @Inject
    public GameService(GameRepository gameRepository,
                       MapService mapService) {
        this.gameRepository = notNull(gameRepository);
        this.mapService = notNull(mapService);
    }

    @Transactional
    public GameEntity startNewGame(String gameName, String mapName) {
        Optional<MapEntity> mapOptional = mapService.find(mapName);

        gameRepository.findByName(gameName)
                .ifPresent(gameEntity -> {
                    throw new IllegalArgumentException("Game " + gameName + " already exists");
                });

        MapEntity map = mapOptional
                .orElseThrow(() -> new EntityNotFoundException("Map '" + mapName + "' not found"));

        GameEntity game = new GameEntity();
        game.setName(gameName);
        game.setMap(map);
        return gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public GameStatus getStatus(String gameName) {
        final GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new EntityNotFoundException("Game '" + gameName + "' not found"));
        return gameEntity.getGameStatus();
    }

    @Transactional
    public void removeGame(String gameName){
        final GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new EntityNotFoundException("Game '" + gameName + "' not found"));
        gameEntity.setGameStatus(GameStatus.ARCHIVED);
    }
}

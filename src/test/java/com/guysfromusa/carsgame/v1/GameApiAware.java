package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.v1.model.Game;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
public interface GameApiAware {

    default Game startNewGame(TestRestTemplate template, String gameToPlay, String mapToPlay) {
        String url = String.join("/", "/v1/games", gameToPlay);
        return template.postForObject(url, mapToPlay, Game.class);
    }

}

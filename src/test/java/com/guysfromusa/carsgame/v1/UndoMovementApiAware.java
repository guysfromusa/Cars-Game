package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.v1.model.UndoNStepPath;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public interface UndoMovementApiAware {

    default ResponseEntity<UndoNStepPath[]> doNMovementBack(TestRestTemplate template, String gameId, String carName, String numberOfStepsBack){
        String url = String.join("/", "/v1/back-movements", gameId, carName, numberOfStepsBack);
        return template.getForEntity(url, UndoNStepPath[].class);
    }
}

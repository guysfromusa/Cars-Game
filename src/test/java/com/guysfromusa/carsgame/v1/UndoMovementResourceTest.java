package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class UndoMovementResourceTest {

    @Inject
    private TestRestTemplate template;

    @Inject
    private ActiveGamesContainer activeGamesContainer;

    @Test
    public void shouldBackCar3Step(){
        //given
        activeGamesContainer.addNewGame("game1");
        activeGamesContainer.getGameState("game1").addNewCar(aCarEntity().name("volvo").positionX(1).positionY(2).build());
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", MovementDto.Operation.LEFT);
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", MovementDto.Operation.RIGHT);
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", MovementDto.Operation.FORWARD);

        //when
        String url = String.join("/", "/v1/back-movements", "game1", "volvo", "3");
        ResponseEntity<CarEntity[]> movements = template.getForEntity(url, CarEntity[].class);

        //then

    }

}
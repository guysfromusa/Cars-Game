package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.game_state.ActiveGamesContainer;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto.Operation;
import com.guysfromusa.carsgame.v1.model.UndoNStepPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static com.guysfromusa.carsgame.entities.CarEntityBuilder.aCarEntity;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

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
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", Operation.LEFT);
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", Operation.RIGHT);
        activeGamesContainer.getGameState("game1").addMovementHistory("volvo", Operation.FORWARD);

        //when
        String url = String.join("/", "/v1/back-movements", "game1", "volvo", "3");
        ResponseEntity<UndoNStepPath[]> movements = template.getForEntity(url, UndoNStepPath[].class);

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
        assertThat(movements.getBody())
                .extracting(UndoNStepPath::getOperation)
                .containsExactly("LEFT", "LEFT", "FORWARD", "LEFT", "RIGHT", "LEFT", "LEFT");
        //TODO : ADD TEST TO GET CARS POSITION WHEN MOVE WILL BE DONE
    }

}
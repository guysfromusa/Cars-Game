package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class MovementsHistoryResourceTest {


    @Inject
    private TestRestTemplate template;

    @Test
    @Sql(value = {"/sql/clean.sql","/sql/historyMovement_Insert.sql"})
    public void shouldReturnOne() {
        //when
        ResponseEntity<MovementHistory[]> movements = template.getForEntity("/v1/movements-history?gameIds=&carNames=FIAT&limitOfRecentStep=", MovementHistory[].class);

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
        assertThat(movements.getBody()).extracting(MovementHistory::getCarName, MovementHistory::getGameName)
                .containsExactly(tuple("FIAT", "game2"));
  }

    @Test
    @Sql(value = {"/sql/clean.sql","/sql/historyMovement_Insert.sql"})
    public void shouldReturnTwo() {
        //when
        ResponseEntity<MovementHistory[]> movements = template.getForEntity("/v1/movements-history?gameIds=game2,game3&carNames=FIAT,MERCEDES&limitOfRecentStep=", MovementHistory[].class);

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
        assertThat(movements.getBody()).extracting(MovementHistory::getCarName, MovementHistory::getGameName)
                .containsExactlyInAnyOrder(tuple("FIAT", "game2"), tuple("MERCEDES", "game3"));
    }

    @Test
    @Sql(value = {"/sql/clean.sql","/sql/historyMovement_Insert.sql"})
    public void shouldReturnTree() {
        //when
        ResponseEntity<MovementHistory[]> movements = template.getForEntity("/v1/movements-history?gameIds=&carNames=&limitOfRecentStep=", MovementHistory[].class);

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
        assertThat(movements.getBody()).extracting(MovementHistory::getCarName, MovementHistory::getGameName)
                .containsExactlyInAnyOrder(tuple("FIAT", "game2"), tuple("MERCEDES", "game3"), tuple("TOYOTA", "game4"));
    }
}
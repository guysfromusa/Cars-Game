package com.guysfromusa.carsgame.v1;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class MovementsHistoryResourceTest {


    @Inject
    private TestRestTemplate template;

    @Test
    @DatabaseSetup("/insert-movements-history.xml")
    public void shouldReturnStatus() {
        //when
        ResponseEntity<List<MovementHistory>> movements = template.getForEntity("/v1/movements-history?gameIds=&carNames=FIAT&limitOfRecentStep=",
                null);

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
    }
}
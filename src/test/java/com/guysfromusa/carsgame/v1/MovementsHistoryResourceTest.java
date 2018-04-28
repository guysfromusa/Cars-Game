package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.v1.model.MovementHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class MovementsHistoryResourceTest {


    @Inject
    private TestRestTemplate template;

    @Test
    public void shouldCreateMap() {
        //when
        ResponseEntity<MovementHistory> movements = template.getForEntity("/v1/movements-history/{gameIds}/{carNames}/{limitOfRecentStep}",
                null,  "1,2,3", "toyota,mercedes", "2");

        //then
        assertThat(movements.getStatusCode()).isEqualTo(OK);
    }
}
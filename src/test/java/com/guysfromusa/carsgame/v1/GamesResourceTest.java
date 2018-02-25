package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class GamesResourceTest {

    @Inject
    private TestRestTemplate template;

    @Test
    public void whenActionSuccessfulShouldReturnCarPositions() {
        //when
        ResponseEntity<String> response = template.getForEntity("/v1/games", String.class);

        //then
        assertThat(response.getBody())
                .isEqualTo("OK");
    }

}
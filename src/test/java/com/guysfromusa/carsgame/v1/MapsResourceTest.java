package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.config.SpringContextConfiguration;
import com.guysfromusa.carsgame.v1.model.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by Robert Mycek, 2018-02-26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringContextConfiguration.class)
public class MapsResourceTest {

    @Inject
    private TestRestTemplate template;

    @Test
    public void shouldCreateMap() {
        //given
        HttpEntity<Map> request = new RequestBuilder<Map>()
                .body(new Map("name", "1,1\n1,0"))
                .build();

        //when
        ResponseEntity<String> response = template.postForEntity("/v1/maps", request, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    public void shouldDeleteMap() {
        //when
        ResponseEntity<Map> response = template
                .exchange("/v1/maps/{name}", DELETE, null, Map.class, "map");

        //then
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

}

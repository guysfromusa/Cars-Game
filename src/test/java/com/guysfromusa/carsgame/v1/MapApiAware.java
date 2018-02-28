package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.RequestBuilder;
import com.guysfromusa.carsgame.v1.model.Map;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
public interface MapApiAware {

    default void addNewMap(TestRestTemplate template, Map map){
        //given
        HttpEntity<Map> request = new RequestBuilder<Map>()
                .body(map)
                .build();

        //when
        template.postForEntity("/v1/maps", request, Long.class);
    }
}

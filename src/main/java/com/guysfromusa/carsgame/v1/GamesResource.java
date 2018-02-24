package com.guysfromusa.carsgame.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Tomasz Bradlo, ${DATE}
 */
@RestController
@RequestMapping(value = "/v1/games", produces = APPLICATION_JSON_UTF8_VALUE)
public class GamesResource {

    @RequestMapping(method = GET)
    public String restIsWorking() {
        return "OK";
    }

}

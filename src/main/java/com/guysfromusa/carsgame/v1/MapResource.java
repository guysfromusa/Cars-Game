package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.entities.Map;
import com.guysfromusa.carsgame.services.MapService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Sebastian Mikucki, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/maps", produces = APPLICATION_JSON_UTF8_VALUE)
public class MapResource {

    private final MapService mapService;

    @Inject
    public MapResource(MapService mapService) {
        this.mapService = notNull(mapService);
    }

    @RequestMapping(method = GET)
    public Iterable<Map> findAllMaps() {
        return mapService.findAll();
    }

}

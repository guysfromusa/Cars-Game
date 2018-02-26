package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.entities.MapEntity;
import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.converters.MapConverter;
import com.guysfromusa.carsgame.v1.model.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Sebastian Mikucki, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/maps", produces = APPLICATION_JSON_UTF8_VALUE)
public class MapsResource {

    private final MapService mapService;

    private final MapConverter mapConverter;

    @Inject
    public MapsResource(MapService mapService, MapConverter mapConverter) {
        this.mapService = notNull(mapService);
        this.mapConverter = notNull(mapConverter);
    }

    @GetMapping
    public List<Map> findAllMaps() {
        return mapService.findAll().stream()
                .map(mapConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Long> createMap(@RequestBody Map map) {
        Long id = mapService.create(mapConverter.toEntity(map)).getId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteMap(@PathVariable Long id) {
        MapEntity map = mapService.findById(id);
        if (map == null) {
            return new ResponseEntity(NOT_FOUND);
        }
        mapService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


}

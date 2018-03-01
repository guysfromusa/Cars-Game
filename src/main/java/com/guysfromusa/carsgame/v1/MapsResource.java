package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.converters.MapConverter;
import com.guysfromusa.carsgame.v1.model.Map;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Sebastian Mikucki, 25.02.18
 */
@RestController
@RequestMapping(value = "/v1/maps", produces = APPLICATION_JSON_UTF8_VALUE)
@Api(value = "maps", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
public class MapsResource {

    private final MapService mapService;

    @Inject
    public MapsResource(MapService mapService) {
        this.mapService = notNull(mapService);
    }

    @GetMapping
    public List<Map> findAllMaps() {
        return mapService.findAll().stream()
                .map(MapConverter::fromEntity)
                .collect(toList());
    }

    @ApiOperation(value = "Create new map")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Map successfully created"),
            @ApiResponse(code = 409, message = "Map with given name exist")})
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createMap(@RequestBody Map map) {
        String name = mapService.create(map.getName(), map.getContent()).getName();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(name)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Delete map")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Map successfully deleted")})
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<?> deleteMap(@PathVariable String name) {
        mapService.deleteByName(name);
        return new ResponseEntity<>(NO_CONTENT);
    }
}

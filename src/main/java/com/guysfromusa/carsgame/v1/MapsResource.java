package com.guysfromusa.carsgame.v1;

import com.guysfromusa.carsgame.services.MapService;
import com.guysfromusa.carsgame.v1.model.Map;
import com.guysfromusa.carsgame.v1.validator.MapValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
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

    private final ConversionService conversionService;
    private final MapValidator mapValidator;

    @Inject
    public MapsResource(MapService mapService, ConversionService conversionService, MapValidator mapValidator) {
        this.mapService = notNull(mapService);
        this.conversionService = notNull(conversionService);
        this.mapValidator = notNull(mapValidator);
    }

    @GetMapping
    public List<Map> findAllMaps() {
        return mapService.findAll().stream()
                .map(mapEntity -> conversionService.convert(mapEntity, Map.class))
                .collect(toList());
    }

    @ApiOperation(value = "Create new map")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Map successfully created"),
            @ApiResponse(code = 409, message = "Map with given name exist")})
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createMap(@Valid @RequestBody Map map) {
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
        mapService.delete(name);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @InitBinder("map")
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(mapValidator);
    }

}

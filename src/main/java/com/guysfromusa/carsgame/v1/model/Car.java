package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.Direction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
public class Car {

    @Getter @Setter
    @ApiModelProperty(notes = "Name of the car")
    private String name;

    @Getter @Setter
    @ApiModelProperty(notes = "Position of the car")
    private Point position;

    @Getter @Setter
    @ApiModelProperty(notes = "Direction of the car")
    private Direction direction;

}
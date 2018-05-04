package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Getter @Setter
    private CarType type;

    @Getter @Setter
    private String game;

    @Getter @Setter
    private boolean crashed;


}
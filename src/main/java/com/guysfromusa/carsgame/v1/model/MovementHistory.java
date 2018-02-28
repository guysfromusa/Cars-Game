package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.Direction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class MovementHistory {

    @Getter
    @Setter
    @ApiModelProperty(notes = "Name of the car")
    private String carName;

    @Getter
    @Setter
    @ApiModelProperty(notes = "Name of the game")
    private String gameName;

    @Getter @Setter
    @ApiModelProperty(notes = "New position of the car")
    private Point position;

    @Getter @Setter
    @ApiModelProperty(notes = "New direction of the car")
    private Direction direction;
}

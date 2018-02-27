package com.guysfromusa.carsgame.v1.model;

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
    @ApiModelProperty(notes = "Position of the car")
    private Point position;
}

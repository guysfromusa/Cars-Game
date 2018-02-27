package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
public class Car {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Point position;

    @Getter @Setter
    private Direction direction;

    @Getter @Setter
    private String type;

}
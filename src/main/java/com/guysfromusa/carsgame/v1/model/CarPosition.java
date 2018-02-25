package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
@AllArgsConstructor
public class CarPosition {

    @Getter @Setter
    private String carName;

    @Getter @Setter
    private Point position;

    @Getter @Setter
    private Direction direction;

}

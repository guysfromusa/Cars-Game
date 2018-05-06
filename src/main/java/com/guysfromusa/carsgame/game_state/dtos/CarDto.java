package com.guysfromusa.carsgame.game_state.dtos;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor //TODO immutable
@ToString
public class CarDto {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Point position;

    @Getter
    @Setter
    private Direction direction;

    @Getter
    @Setter
    private CarType type;

    @Getter
    @Setter
    private String game;

    @Getter
    @Setter
    private boolean crashed;

    @Getter
    @Setter
    @Builder.Default
    private volatile boolean undoInProcess = false;

}
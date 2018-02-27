package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.TurnSide;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
public class Movement {

    @Getter @Setter
    private Type type;

    @Getter @Setter
    private TurnSide turnSide;

    @Getter @Setter
    private Integer undoSteps;

    public enum Type {
        TURN, MOVE, UNDO
    }

}
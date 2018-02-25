package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.model.TurnSide;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.guysfromusa.carsgame.v1.model.Movement.Type.MOVE;
import static com.guysfromusa.carsgame.v1.model.Movement.Type.TURN;
import static com.guysfromusa.carsgame.v1.model.Movement.Type.UNDO;

/**
 * Created by Tomasz Bradlo, 25.02.18
 */
@NoArgsConstructor
@AllArgsConstructor
public class Movement {

    @Getter @Setter
    private Type type;

    @Getter @Setter
    private TurnSide turnSide;

    @Getter @Setter
    private Integer undoSteps;

    public static Movement ofTurn(TurnSide side){
        return new Movement(TURN, side, null);
    }

    public static Movement ofMove(){
        return new Movement(MOVE, null, null);
    }

    public static Movement ofUndo(int noOfSteps){
        return new Movement(UNDO, null, noOfSteps);
    }

    public enum Type {
        TURN, MOVE, UNDO
    }

}
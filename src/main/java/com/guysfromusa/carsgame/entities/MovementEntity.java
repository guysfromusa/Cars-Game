package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.model.MovementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Entity
@NoArgsConstructor
public class MovementEntity {

    @Id
    @Getter @Setter private Long id;

//TODO
//    @Getter @Setter private CarEntity car;
//
//    @Getter @Setter private GameEntity game;

    @Getter @Setter private MovementType type;

    @Getter @Setter private int xStartPosition;

    @Getter @Setter private int yStartPosition;

    @Getter @Setter private Direction startDirection;

    @Getter @Setter private int xEndPosition;

    @Getter @Setter private int yEndPosition;

    @Getter @Setter private Direction endDirection;

    @Getter @Setter private boolean crashed;

}

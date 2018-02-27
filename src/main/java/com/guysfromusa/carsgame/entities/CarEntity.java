package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Entity
@Table(name = "CAR")
@NoArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    @Getter
    @Setter
    private GameEntity game;

    @Column
    @Enumerated(STRING)
    @Getter
    @Setter
    private Direction direction;

    @Getter @Setter
    private Integer positionX;

    @Getter @Setter
    private Integer positionY;

    @Getter @Setter
    @Enumerated(STRING)
    private CarType carType;
}

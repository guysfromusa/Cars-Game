package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "GAME")
public class GameEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(targetEntity = CarEntity.class, mappedBy="game")
    @Getter @Setter
    private Set<CarEntity> cars;

    @OneToMany(targetEntity = MovementEntity.class, mappedBy="game")
    @Getter @Setter
    private Set<MovementEntity> movements;
}

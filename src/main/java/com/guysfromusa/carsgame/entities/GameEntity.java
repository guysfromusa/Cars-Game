package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
@Entity
@Table(name = "GAME")
@NoArgsConstructor
public class GameEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @OneToMany(mappedBy = "game", targetEntity = CarEntity.class)
    @Getter
    @Setter
    private Set<CarEntity> cars;

    @ManyToOne
    @JoinColumn(name = "MAP_ID")
    @Getter
    @Setter
    private MapEntity map;
}

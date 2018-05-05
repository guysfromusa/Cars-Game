package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.GameStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Tomasz Bradlo, 27.02.18
 */
@Entity
@Table(name = "GAME")
@NoArgsConstructor
@ToString
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

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.RUNNING;
}

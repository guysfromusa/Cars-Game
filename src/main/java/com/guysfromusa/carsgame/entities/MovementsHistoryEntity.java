package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Entity(name = "MOVEMENT_HISTORY")
public class MovementsHistoryEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="GAME_ID", referencedColumnName = "ID")
    @Getter
    @Setter
    private GameEntity game;

    @OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="CAR_ID", referencedColumnName = "ID")
    @Getter
    @Setter
    private CarEntity car;

    @Column(name="POSITION", nullable = false)
    @Getter
    @Setter
    private Point position;

    private LocalDateTime createDateTime;

    public MovementsHistoryEntity() {
    }

    @PrePersist
    void createAt(){
        createDateTime = now();
    }

    public MovementsHistoryEntity(GameEntity game, CarEntity car, Point position) {
        this.game = game;
        this.car = car;
        this.position = position;
    }

    public static MovementsHistoryEntity withPosition(MovementsHistoryEntity movementsHistoryEntity, Point position){
        return new MovementsHistoryEntity(movementsHistoryEntity.game, movementsHistoryEntity.car, position);
    }

}

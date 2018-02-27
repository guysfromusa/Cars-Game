package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.Setter;

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

    @Column(name="POSITION_X", nullable = false)
    @Getter
    @Setter
    private Integer positionX;

    @Column(name="POSITION_Y", nullable = false)
    @Getter
    @Setter
    private Integer positionY;

    @Column(name="CREATE_DATA_TIME")
    @Getter
    private LocalDateTime createDateTime;

    public MovementsHistoryEntity() {
    }

    @PrePersist
    void createAt(){
        createDateTime = now();
    }

    public MovementsHistoryEntity(GameEntity game, CarEntity car, Integer positionX, Integer positionY) {
        this.game = game;
        this.car = car;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public static MovementsHistoryEntity withPosition(MovementsHistoryEntity movementsHistoryEntity, Integer positionX, Integer positionY){
        return new MovementsHistoryEntity(movementsHistoryEntity.game, movementsHistoryEntity.car, positionX, positionY);
    }

}

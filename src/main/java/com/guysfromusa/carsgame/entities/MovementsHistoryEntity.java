package com.guysfromusa.carsgame.entities;

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
    private GameEntity game;

    @OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="CAR_ID", referencedColumnName = "ID")
    private CarEntity car;

    @Column(name="POSITION", nullable = false)
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }
}

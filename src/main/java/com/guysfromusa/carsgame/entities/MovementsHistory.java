package com.guysfromusa.carsgame.entities;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Entity
public class MovementsHistory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private String carName;

    @Column(nullable = false)
    private String mapName;

    @Column(nullable = false)
    private Point position;

    public MovementsHistory() {
    }

    public MovementsHistory(Long gameId, String carName, String mapName, Point position) {
        this.gameId = gameId;
        this.carName = carName;
        this.mapName = mapName;
        this.position = position;
    }

    public MovementsHistory withPosition(Point position){
        return new MovementsHistory(this.gameId, this.carName, this.mapName, position);
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return  new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .append("gameId", gameId)
                .append("carName", carName)
                .append("mapName", mapName)
                .append("postition", position)
                .toString();
    }
}

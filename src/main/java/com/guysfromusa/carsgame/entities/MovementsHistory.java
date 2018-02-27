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

@Entity(name = "MOVEMENT_HISTORY")
public class MovementsHistory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String gameId;

    @Column(nullable = false)
    private String carName;

    @Column(nullable = false)
    private String mapName;

    @Column(nullable = false)
    private Point position;

    public MovementsHistory() {
    }

    public MovementsHistory(String gameId,String mapName, String carName, Point position) {
        this.gameId = gameId;
        this.mapName = mapName;
        this.carName = carName;
        this.position = position;
    }

    public static MovementsHistory withPosition(MovementsHistory movementsHistory, Point position){
        return new MovementsHistory(movementsHistory.gameId, movementsHistory.mapName, movementsHistory.carName, position);
    }

    public Long getId() {
        return id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
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

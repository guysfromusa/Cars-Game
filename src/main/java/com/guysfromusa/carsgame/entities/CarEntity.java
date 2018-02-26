package com.guysfromusa.carsgame.entities;


import com.guysfromusa.carsgame.entities.enums.CarType;

import javax.persistence.*;

@Entity
public class CarEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private CarType carType;

    @Column(name = "POSITION_X")
    private Integer positionX;

    @Column(name = "POSITION_Y")
    private Integer positionY;

    @Column(name= "NAME", unique = true)
    private String name;

    @Column(name="CRASHED")
    private boolean crashed;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }
}

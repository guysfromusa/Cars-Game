package com.guysfromusa.carsgame.v1.model.builder;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;

/**
 * Created by Konrad Rys, 04.05.2018
 */
public final class CarBuilder {
    private String name;
    private Point position;
    private Direction direction;
    private CarType type;
    private String game;
    private boolean crashed;

    private CarBuilder() {
    }

    public static CarBuilder aCar() {
        return new CarBuilder();
    }

    public CarBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CarBuilder position(Point position) {
        this.position = position;
        return this;
    }

    public CarBuilder direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public CarBuilder type(CarType type) {
        this.type = type;
        return this;
    }

    public CarBuilder game(String game) {
        this.game = game;
        return this;
    }

    public CarBuilder crashed(boolean crashed) {
        this.crashed = crashed;
        return this;
    }

    public Car build() {
        Car car = new Car();
        car.setName(name);
        car.setPosition(position);
        car.setDirection(direction);
        car.setType(type);
        car.setGame(game);
        car.setCrashed(crashed);
        return car;
    }
}

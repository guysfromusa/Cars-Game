package com.guysfromusa.carsgame.v1.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Point {

    @Getter @Setter
    private Integer x;

    @Getter @Setter
    private Integer y;
}

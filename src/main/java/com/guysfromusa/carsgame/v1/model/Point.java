package com.guysfromusa.carsgame.v1.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Point {

    @Getter @Setter
    private Integer x;

    @Getter @Setter
    private Integer y;
}

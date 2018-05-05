package com.guysfromusa.carsgame.v1.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Point {

    @Getter @Setter
    private Integer x;

    @Getter @Setter
    private Integer y;
}

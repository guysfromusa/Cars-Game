package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Entity( name = "CAR")
@NoArgsConstructor
public class CarEntity {

    @Getter @Setter
    @Id
    private Long id;

    @Getter @Setter
    private String name;

    //TODO finish
}

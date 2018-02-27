package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.CarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Entity
@Table(name = "CAR")
@NoArgsConstructor
public class CarEntity {

    @Getter @Setter
    @Id
    @GeneratedValue
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Integer positionX;

    @Getter @Setter
    private Integer positionY;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private CarType carType;
}

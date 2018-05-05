package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.model.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static java.util.Objects.nonNull;
import static javax.persistence.EnumType.STRING;

/**
 * Created by Tomasz Bradlo, 26.02.18
 */
@Entity
@Table(name = "CAR")
@NoArgsConstructor
public class CarEntity {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String name;

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    @Getter
    @Setter
    private GameEntity game;

    @Column
    @Enumerated(STRING)
    @Getter
    @Setter
    private Direction direction;

    @Getter @Setter
    private Integer positionX;

    @Getter @Setter
    private Integer positionY;

    @Getter @Setter
    @Enumerated(STRING)
    private CarType carType;

    @Getter @Setter
    private boolean crashed;

    //to avoid stackOverFlow
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("game", nonNull(game) ? game.getName() : null)
                .append("direction", direction)
                .append("positionX", positionX)
                .append("positionY", positionY)
                .append("carType", carType)
                .append("crashed", crashed)
                .toString();
    }
}

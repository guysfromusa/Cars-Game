package com.guysfromusa.carsgame.entities;

import com.guysfromusa.carsgame.model.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Created by Dominik Zurek, 26.02.18
 */

@Entity(name = "MOVEMENT_HISTORY")
@Table(name = "MOVEMENT_HISTORY")
@NoArgsConstructor
public class MovementsHistoryEntity {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="GAME_ID", referencedColumnName = "ID")
    @Getter
    @Setter
    private GameEntity game;

    @OneToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="CAR_ID", referencedColumnName = "ID")
    @Getter
    @Setter
    private CarEntity car;

    @Column(name="NEW_POSITION_X", nullable = false)
    @Getter
    @Setter
    private Integer positionX;

    @Column(name="NEW_POSITION_Y", nullable = false)
    @Getter
    @Setter
    private Integer positionY;

    @Column(name="NEW_DIRECTION", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Direction direction;

    @Column(name="CREATE_DATA_TIME")
    @Getter
    private LocalDateTime createDateTime;

    @PrePersist
    void createAt(){
        createDateTime = now();
    }

}

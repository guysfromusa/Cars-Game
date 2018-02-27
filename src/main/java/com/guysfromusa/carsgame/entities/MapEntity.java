package com.guysfromusa.carsgame.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "MAP")
@NoArgsConstructor
@ToString
public class MapEntity {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false)
    private String content;

    @Setter
    @Getter
    @Column(nullable = false)
    private boolean deleted;

    public MapEntity(String name, String content) {
        this.name = name;
        this.content = content;
    }
}

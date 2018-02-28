package com.guysfromusa.carsgame.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Tomasz Bradlo, 28.02.18
 */
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String mapName;

}

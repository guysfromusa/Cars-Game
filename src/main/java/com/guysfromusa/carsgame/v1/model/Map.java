package com.guysfromusa.carsgame.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Robert Mycek, 2018-02-26
 */
@AllArgsConstructor
@NoArgsConstructor
public class Map {

    @Setter
    @Getter
    @NotEmpty
    private String name;

    @Setter
    @Getter
    @NotEmpty
    private String content;
}

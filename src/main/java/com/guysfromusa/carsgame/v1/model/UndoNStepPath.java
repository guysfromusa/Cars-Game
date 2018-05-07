package com.guysfromusa.carsgame.v1.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UndoNStepPath {

    @Getter
    private String operation;
}

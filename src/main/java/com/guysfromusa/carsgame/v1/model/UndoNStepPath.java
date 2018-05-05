package com.guysfromusa.carsgame.v1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UndoNStepPath {

    @Getter
    private String operation;
}

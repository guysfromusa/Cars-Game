package com.guysfromusa.carsgame.v1.model;

import com.guysfromusa.carsgame.exceptions.ApiError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Konrad Rys, 05.05.2018
 */

public class MovementResult{

    @Getter @Setter
    private List<Car> cars;

    @Getter @Setter
    private ApiError apiError;
}

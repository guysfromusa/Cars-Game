package com.guysfromusa.carsgame.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by Sebastian Mikucki, 28.02.18
 */
@Data
@Builder
public class ApiError {

    private final String message;
    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime date;

}

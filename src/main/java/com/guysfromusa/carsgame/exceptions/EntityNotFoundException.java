package com.guysfromusa.carsgame.exceptions;

/**
 * Created by Sebastian Mikucki, 28.02.18
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}

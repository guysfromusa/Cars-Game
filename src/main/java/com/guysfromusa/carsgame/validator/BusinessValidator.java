package com.guysfromusa.carsgame.validator;

/**
 * Created by Sebastian Mikucki, 04.05.18
 */
@FunctionalInterface
public interface BusinessValidator<T> {

    void validate(T t);

}

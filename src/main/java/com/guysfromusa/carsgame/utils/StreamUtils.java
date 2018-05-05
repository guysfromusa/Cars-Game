package com.guysfromusa.carsgame.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    public static <T, R> List<R> convert(Collection<T> collection, Function<T, R> mapF){
        return collection.stream().map(mapF).collect(toList());
    }

    public static <T, R> boolean anyMatch(Collection<T> collection, Function<T, R> mapFunction, Predicate<R> predicate){
        return collection.stream().map(mapFunction).anyMatch(predicate);
    }




}

package com.guysfromusa.carsgame.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
public class StreamUtils {

    public static <T, R> List<R> convert(Collection<T> collection, Function<T, R> mapF){
        return collection.stream().map(mapF).collect(toList());
    }


}

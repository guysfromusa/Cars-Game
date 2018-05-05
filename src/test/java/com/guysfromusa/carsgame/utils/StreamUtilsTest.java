package com.guysfromusa.carsgame.utils;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.function.Function.identity;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian Mikucki, 30.04.18
 */
public class StreamUtilsTest {

    @Test
    public void shouldConvertListStringToListInteger() {
        //given
        List<String> list = Arrays.asList("1", "2", "3");

        //when
        List<Integer> result = StreamUtils.convert(list, Integer::parseInt);

        //then
        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    public void shouldConvertSetOfIntToListOfString() {
        //given
        Set<Integer> list = Sets.newHashSet(1, 2, 3);

        //when
        List<String> result = StreamUtils.convert(list, String::valueOf);

        //then
        assertThat(result).containsExactly("1", "2", "3");
    }

    @Test
    public void shouldMatch() {
        //given
        Collection<String> objects = Arrays.asList("aaa", "bbb", "bca");
        Predicate<String> testP = s -> s.equals("bca");

        //when
        boolean result = StreamUtils.anyMatch(objects, identity(), testP);

        //when
        assertThat(result).isTrue();
    }

    @Test
    public void shouldNotMatch() {
        //given
        Collection<String> objects = Arrays.asList("aaa", "bbb", "bca");
        Predicate<String> testP = s -> s.equals("aaaa");

        //when
        boolean result = StreamUtils.anyMatch(objects, identity(), testP);

        //when
        assertThat(result).isFalse();
    }
}
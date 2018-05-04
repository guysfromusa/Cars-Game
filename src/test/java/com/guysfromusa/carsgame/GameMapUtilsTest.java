package com.guysfromusa.carsgame;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class GameMapUtilsTest {

    @Test
    public void shouldConvertStringContentToIntegerArray() {
        //given
        String content = "1,1\n1,0";

        //when
        Integer[][] mapMatrixContent = GameMapUtils.getMapMatrixFromContent(content);

        //then
        assertEquals(2, mapMatrixContent.length);
        assertEquals(2, mapMatrixContent[0].length);
        assertEquals(1, mapMatrixContent[0][0], 0);
        assertEquals(1, mapMatrixContent[0][1], 0);
        assertEquals(1, mapMatrixContent[1][0], 0);
        assertEquals(0, mapMatrixContent[1][1], 0);
    }

    @Test
    public void isReachable() {
        assertThat(GameMapUtils.isReachable(new Integer[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}})).isTrue();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{1, 1}, {1, 1}})).isTrue();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{1}})).isTrue();

        assertThat(GameMapUtils.isReachable(new Integer[][]{})).isFalse();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{0}})).isFalse();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{0, 0}, {0, 0}})).isFalse();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{0, 1}, {1, 0}})).isFalse();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{0, 1, 1}, {0, 1, 1}, {1, 0, 0}})).isFalse();
        assertThat(GameMapUtils.isReachable(new Integer[][]{{1, 1, 1}, {}, {1, 1, 1}})).isFalse();
    }
}
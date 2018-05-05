package com.guysfromusa.carsgame;

import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameMapUtilsTest {

    @Test
    public void shouldNormalizeCarAvailableFieldsToOne() {
        //given
        String content = "3,2\nx,0";

        //when
        Integer[][] map = GameMapUtils.getMapMatrixFromContent(content);

        //then
        assertThat(map).isEqualTo(new Integer[][]{{1, 1}, {1, 0}});
    }

    @Test
    public void shouldConvertStringContentToIntegerArray() {
        //given
        String content = "1,1\n1,0";

        //when
        Integer[][] map = GameMapUtils.getMapMatrixFromContent(content);

        //then
        assertThat(map).isEqualTo(new Integer[][]{{1, 1}, {1, 0}});
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

    @Test
    public void shouldPositionBeValidOnGameMapWhenCarOnRoad(){
        //given
        Integer[][] gameMapContent = {{1,1}, {1,0}, {1,0}, {1,1}};
        Point startingPoint = new Point(1, 3);

        //when
        boolean positionValidOnGameMap = GameMapUtils.isPointOnRoad(gameMapContent, startingPoint);

        //then
        assertTrue(positionValidOnGameMap);
    }

    @Test
    public void shouldNotBeOnRoadGameMap(){
        //given
        Integer[][] gameMapContent = {{1,1}, {1,0}, {1,0}, {1,1}};
        Point startingPoint = new Point(5, 3);

        //when
        boolean positionValidOnGameMap = GameMapUtils.isPointOnRoad(gameMapContent, startingPoint);

        //then
        assertFalse(positionValidOnGameMap);
    }
}
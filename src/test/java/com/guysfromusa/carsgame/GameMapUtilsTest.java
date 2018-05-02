package com.guysfromusa.carsgame;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameMapUtilsTest {

    @Test
    public void shouldConvertStringContentToIntegerArray(){
        //given
        String content = "1,1\n1,0";

        //when
        Integer[][] mapMatrixContent = GameMapUtils.getMapMatrixContent(content);

        //then
        assertEquals(2, mapMatrixContent.length);
        assertEquals(2, mapMatrixContent[0].length);
        assertEquals(1, mapMatrixContent[0][0], 0);
        assertEquals(1, mapMatrixContent[0][1], 0);
        assertEquals(1, mapMatrixContent[1][0], 0);
        assertEquals(0, mapMatrixContent[1][1], 0);
    }

}
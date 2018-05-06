package com.guysfromusa.carsgame.entities.enums;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Sebastian Mikucki, 06.05.18
 */
public class CarTypeTest {

    @Test
    public void shouldReturnTrueIfNumberOfStepsIsValid() {
        assertThat(CarType.MONSTER.isValidStepsPerMove(0)).isTrue();
        assertThat(CarType.NORMAL.isValidStepsPerMove(1)).isTrue();
        assertThat(CarType.RACER.isValidStepsPerMove(1)).isTrue();
        assertThat(CarType.RACER.isValidStepsPerMove(2)).isTrue();
    }

    @Test
    public void shouldReturnFalseIfNumberOfStepsIsInValid() {
        assertThat(CarType.MONSTER.isValidStepsPerMove(-1)).isFalse();
        assertThat(CarType.NORMAL.isValidStepsPerMove(2)).isFalse();
        assertThat(CarType.RACER.isValidStepsPerMove(-2)).isFalse();
        assertThat(CarType.RACER.isValidStepsPerMove(3)).isFalse();
    }
}
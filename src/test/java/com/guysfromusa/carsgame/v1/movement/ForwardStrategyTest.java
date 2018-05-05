package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Car;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by Konrad Rys, 05.05.2018
 */
public class ForwardStrategyTest {

    private ForwardStrategy forwardStrategy = new ForwardStrategy();

    @Test
    public void whenTwoStepsForwardOnSouth_shouldChangePositionAccordingly(){
        //given
        Integer[][] gameMap = {{1,0, 0}, {1, 0, 0}, {1,1,1}};
        Point startPoint = new Point(0,0);
        Integer steps = 2;
        Direction south = Direction.SOUTH;
        Car car = createCar(startPoint, south);
        Movement movement = createMovement(steps);
        
        //when
        boolean isMoved = forwardStrategy.execute(car, gameMap, movement);

        //then
        assertThat(car.getPosition())
                .extracting(Point::getX, Point::getY).containsExactly(0, 2);
    }

    private Car createCar(Point point, Direction direction){
        return Car.builder()
                .direction(direction)
                .position(point)
                .build();
    }
    
    private Movement createMovement(Integer steps){
        return Movement.newMovement(Movement.Operation.FORWARD, steps);
    }

}
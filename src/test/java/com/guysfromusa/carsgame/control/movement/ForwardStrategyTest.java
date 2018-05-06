package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertFalse;


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
        CarDto car = createCar(startPoint, south);
        Movement movement = createMovement(steps);
        
        //when
        MoveResult moveResult = forwardStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult.getMoveStatus()).isEqualTo(SUCCESS);
        assertThat(moveResult.getNewPosition())
                .extracting(Point::getX, Point::getY).containsExactly(0, 2);
    }

    @Test
    public void whenTwoStepsForwardOnEast_shouldChangePositionAccordingly(){
        //given
        Integer[][] gameMap = {{1, 1, 1}};
        Point startPoint = new Point(0,0);
        Integer steps = 2;
        Direction east = Direction.EAST;
        CarDto car = createCar(startPoint, east);
        Movement movement = createMovement(steps);

        //when
        MoveResult moveResult = forwardStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult.getMoveStatus()).isEqualTo(SUCCESS);
        assertThat(moveResult.getNewPosition())
                .extracting(Point::getX, Point::getY).containsExactly(2, 0);
    }

    @Test
    public void whenDriveIntoWall_shouldMoveBeFailed(){
        //given
        Integer[][] gameMap = {{1, 1, 1}};
        Point startPoint = new Point(0,0);
        Integer steps = 2;
        Direction south = Direction.NORTH;
        CarDto car = createCar(startPoint, south);
        Movement movement = createMovement(steps);

        //when
        MoveResult moveResult = forwardStrategy.execute(car, gameMap, movement);

        //then
        assertFalse(moveResult.isWall());
    }

    private CarDto createCar(Point point, Direction direction){
        return CarDto.builder()
                .direction(direction)
                .position(point)
                .build();
    }
    
    private Movement createMovement(Integer steps){
        return Movement.newMovement(Movement.Operation.FORWARD, steps);
    }

}
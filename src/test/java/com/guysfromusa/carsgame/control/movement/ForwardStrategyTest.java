package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static com.guysfromusa.carsgame.control.MoveStatus.CRASHED_INTO_WALL;
import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.model.Direction.EAST;
import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static com.guysfromusa.carsgame.model.Direction.SOUTH;
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
        CarDto car = createCar(startPoint, south);
        Movement movement = createMovement(steps);
        
        //when
        MoveResult moveResult = forwardStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult)
                .extracting(MoveResult::getCarName, MoveResult::getMoveStatus, MoveResult::getNewDirection, MoveResult::getNewPosition, MoveResult::isWall)
                .containsExactly("car1", SUCCESS, SOUTH, new Point(0, 2), false);
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
        assertThat(moveResult)
                .extracting(MoveResult::getCarName, MoveResult::getMoveStatus, MoveResult::getNewDirection, MoveResult::getNewPosition, MoveResult::isWall)
                .containsExactly("car1", SUCCESS, EAST, new Point(2, 0), false);
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
        assertThat(moveResult)
                .extracting(MoveResult::getCarName, MoveResult::getMoveStatus, MoveResult::getNewDirection, MoveResult::getNewPosition, MoveResult::isWall)
                .containsExactly("car1", CRASHED_INTO_WALL, NORTH, new Point(0, 0), true);
    }

    private CarDto createCar(Point point, Direction direction){
        return CarDto.builder()
                .direction(direction)
                .name("car1")
                .position(point)
                .build();
    }
    
    private Movement createMovement(Integer steps){
        return Movement.newMovement(Movement.Operation.FORWARD, steps);
    }

}
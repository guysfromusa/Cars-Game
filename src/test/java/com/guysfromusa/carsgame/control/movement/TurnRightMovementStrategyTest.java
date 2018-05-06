package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.model.Direction.EAST;
import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Konrad Rys, 06.05.2018
 */
public class TurnRightMovementStrategyTest {

    private TurnRightMovementStrategy turnRightMovementStrategy = new TurnRightMovementStrategy();

    @Test
    public void whenNorthDirectionAndTurnRight_shouldChangeDirectionToEast(){
        //given
        Integer[][] gameMap = {{1,0, 0}, {1, 0, 0}, {1,1,1}};
        Point startPoint = new Point(0,0);
        Direction north = Direction.NORTH;
        Movement.Operation right = Movement.Operation.RIGHT;
        CarDto car = createCar(startPoint, north);
        Movement movement = createMovement(right);

        //when
        MoveResult moveResult = turnRightMovementStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult)
                .extracting(MoveResult::getCarName, MoveResult::getMoveStatus, MoveResult::getNewDirection, MoveResult::getNewPosition, MoveResult::isWall)
                .containsExactly("car1", SUCCESS, EAST, new Point(0, 0), false);

    }

    private CarDto createCar(Point point, Direction direction){
        return CarDto.builder()
                .direction(direction)
                .name("car1")
                .position(point)
                .build();
    }

    private Movement createMovement(Movement.Operation operation){
        return Movement.newMovement(operation);
    }

}
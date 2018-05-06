package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.control.MoveStatus;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by Konrad Rys, 05.05.2018
 */
public class TurnLeftMovementStrategyTest {

    private TurnLeftMovementStrategy turnLeftMovementStrategy = new TurnLeftMovementStrategy();

    @Test
    public void whenNorthDirectionAndTurnLeft_shouldChangeDirectionToWest(){
        //given
        Integer[][] gameMap = {{1,0, 0}, {1, 0, 0}, {1,1,1}};
        Point startPoint = new Point(0,0);
        Direction north = Direction.NORTH;
        Movement.Operation left = Movement.Operation.LEFT;
        CarDto car = createCar(startPoint, north);
        Movement movement = createMovement(left);

        //when
        MoveResult moveResult = turnLeftMovementStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult.getMoveStatus()).isEqualTo(MoveStatus.SUCCESS);
        assertThat(moveResult.getNewDirection()).isEqualTo(Direction.WEST);
        assertThat(moveResult.getNewPosition())
                .extracting(Point::getX, Point::getY).containsExactly(0, 0);

    }

    private CarDto createCar(Point point, Direction direction){
        return CarDto.builder()
                .direction(direction)
                .position(point)
                .build();
    }

    private Movement createMovement(Movement.Operation operation){
        return Movement.newMovement(operation);
    }
}
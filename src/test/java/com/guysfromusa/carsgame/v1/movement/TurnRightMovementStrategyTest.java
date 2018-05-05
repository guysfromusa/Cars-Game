package com.guysfromusa.carsgame.v1.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.Movement;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

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
        boolean isMoveSuccess = turnRightMovementStrategy.execute(car, gameMap, movement);

        //then
        assertThat(isMoveSuccess).isTrue();
        assertThat(car.getDirection()).isEqualTo(Direction.EAST);
        assertThat(car.getPosition())
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
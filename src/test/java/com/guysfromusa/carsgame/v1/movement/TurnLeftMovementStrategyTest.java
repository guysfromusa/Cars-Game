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
public class TurnLeftMovementStrategyTest {

    private TurnLeftMovementStrategy turnLeftMovementStrategy = new TurnLeftMovementStrategy();

    @Test
    public void whenNorthDirectionAndTurnLeft_shouldChangeDirectionToWest(){
        //given
        Integer[][] gameMap = {{1,0, 0}, {1, 0, 0}, {1,1,1}};
        Point startPoint = new Point(0,0);
        Direction north = Direction.NORTH;
        Movement.Operation left = Movement.Operation.LEFT;
        Car car = createCar(startPoint, north);
        Movement movement = createMovement(left);

        //when
        boolean isMoveSuccess = turnLeftMovementStrategy.execute(car, gameMap, movement);

        //then
        assertThat(isMoveSuccess).isTrue();
        assertThat(car.getDirection()).isEqualTo(Direction.WEST);
        assertThat(car.getPosition())
                .extracting(Point::getX, Point::getY).containsExactly(0, 0);

    }

    private Car createCar(Point point, Direction direction){
        return Car.builder()
                .direction(direction)
                .position(point)
                .build();
    }

    private Movement createMovement(Movement.Operation operation){
        return Movement.newMovement(operation);
    }
}
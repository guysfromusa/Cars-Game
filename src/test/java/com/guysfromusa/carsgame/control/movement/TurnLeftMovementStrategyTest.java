package com.guysfromusa.carsgame.control.movement;

import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.MovementDto;
import com.guysfromusa.carsgame.model.Direction;
import com.guysfromusa.carsgame.v1.model.Point;
import org.junit.Test;

import static com.guysfromusa.carsgame.control.MoveStatus.SUCCESS;
import static com.guysfromusa.carsgame.game_state.dtos.MovementDto.newMovementDto;
import static com.guysfromusa.carsgame.model.Direction.WEST;
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
        MovementDto.Operation left = MovementDto.Operation.LEFT;
        CarDto car = createCar(startPoint, north);
        MovementDto movement = createMovement(left);

        //when
        MoveResult moveResult = turnLeftMovementStrategy.execute(car, gameMap, movement);

        //then
        assertThat(moveResult)
                .extracting(MoveResult::getCarName, MoveResult::getMoveStatus, MoveResult::getNewDirection, MoveResult::getNewPosition, MoveResult::isWall)
                .containsExactly("car1", SUCCESS, WEST, new Point(0, 0), false);

    }

    private CarDto createCar(Point point, Direction direction){
        return CarDto.builder()
                .direction(direction)
                .position(point)
                .name("car1")
                .build();
    }

    private MovementDto createMovement(MovementDto.Operation operation){
        return newMovementDto(operation);
    }
}
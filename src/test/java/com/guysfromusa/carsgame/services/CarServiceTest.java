package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.MovementsHistoryEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.v1.validator.CarGameAdditionValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Objects;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static com.guysfromusa.carsgame.model.Direction.NORTH;
import static com.guysfromusa.carsgame.model.Direction.WEST;
import static com.guysfromusa.carsgame.model.TurnSide.LEFT;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MovementsHistoryRepository movementsHistoryRepository;

    @Mock
    private CarGameAdditionValidator carGameAdditionValidator;

    @InjectMocks
    private CarService carService;

    @Test
    public void shouldAddNewCar(){
        //given
        CarType monsterCarType = MONSTER;
        String carName = "My-First-CarEntity-Added";

        //when
        carService.addCar(monsterCarType, carName);

        //then
        ArgumentCaptor<CarEntity> carEntityArgumentCaptor = ArgumentCaptor.forClass(CarEntity.class);
        verify(carRepository).save(carEntityArgumentCaptor.capture());

        CarEntity capturedCarEntity = carEntityArgumentCaptor.getValue();
        assertEquals(carName, capturedCarEntity.getName());
    }

    @Test
    public void shouldDeleteCar(){
        //given
        String givenCarName = "My-First-To-Be-Deleted";

        //when
        carService.deleteCarByName(givenCarName);

        //then
        ArgumentCaptor<String> carNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(carRepository).deleteByName(carNameArgumentCaptor.capture());
        String capturedCarName = carNameArgumentCaptor.getValue();

        assertEquals(givenCarName, capturedCarName);
    }

    @Test
    public void shouldLoadAtLeastOneCar(){
        //given
        String carName = "My-Second-Car";
        List<CarEntity> cars = singletonList(new CarEntity() {{
            this.setName(carName);
        }});
        when(carRepository.findAll()).thenReturn(cars);

        //when
        Iterable<CarEntity> carEntities = carService.loadAllCars();

        //then
        assertThat(carEntities).isNotEmpty().extracting(CarEntity::getName).contains(carName);
    }

    @Test
    public void shouldTurnCar(){
        //given
        CarEntity carEntity = new CarEntity();
        carEntity.setName("car1");
        carEntity.setDirection(NORTH);
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");
        when(carRepository.findByGameAndName(any(), any()))
                .thenReturn(of(carEntity));
        when(gameRepository.findByName(any()))
                .thenReturn(of(gameEntity));

        //when
        carService.turnCar("game1", "car1", LEFT);

        //then
        ArgumentCaptor<MovementsHistoryEntity> captor = ArgumentCaptor.forClass(MovementsHistoryEntity.class);
        verify(movementsHistoryRepository).save(captor.capture());

        MovementsHistoryEntity movement = captor.getValue();
        assertEquals(WEST, movement.getDirection());
        assertEquals(WEST, carEntity.getDirection());
    }

    @Test
    public void shouldAddCarToGame(){
        //given
        String carName = "My-Second-Car";
        String carGame = "Car-Game";
        Point startingPoint = new Point(1,1);
        CarEntity carEntity = new CarEntity();
        carEntity.setName(carName);
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");

        when(gameRepository.findByName(any())).thenReturn(of(new GameEntity()));
        when(carRepository.findByName(eq(carName))).thenReturn(of(carEntity));

        when(gameRepository.findByName(any())).thenReturn(of(gameEntity));

        //when
        carService.addCarToGame(carName, carGame, startingPoint);

        //then
        verify(carRepository).save(argThat(new ArgumentMatcher<CarEntity>() {
            @Override
            public boolean matches(Object argument) {
                CarEntity carEntityArgument = (CarEntity) argument;
                String carNameCaptor = carEntityArgument.getName();
                Integer positionX = carEntityArgument.getPositionX();
                Integer positionY = carEntityArgument.getPositionY();
                return Objects.equals(carName, carNameCaptor) && Objects.equals(1, positionX)
                        && Objects.equals(1, positionY);
            }
        }));
    }

    @Test
    public void shouldRepairCar(){
        //given
        String carName = "Dodge";

        CarEntity carEntity = createCarEntity();
        when(carRepository.findByName(carName)).thenReturn(of(carEntity));
        when(carRepository.save(carEntity)).thenReturn(carEntity);

        //when
        CarEntity repairedCar = carService.repairCar(carName);

        //then
        assertFalse(repairedCar.isCrashed());
    }

    private CarEntity createCarEntity() {
        CarEntity carEntity = new CarEntity();
        carEntity.setCrashed(true);
        return carEntity;
    }

}
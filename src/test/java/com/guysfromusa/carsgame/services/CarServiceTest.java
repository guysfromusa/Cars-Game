package com.guysfromusa.carsgame.services;

import com.guysfromusa.carsgame.entities.CarEntity;
import com.guysfromusa.carsgame.entities.GameEntity;
import com.guysfromusa.carsgame.entities.enums.CarType;
import com.guysfromusa.carsgame.game_state.dtos.CarDto;
import com.guysfromusa.carsgame.game_state.dtos.GameState;
import com.guysfromusa.carsgame.repositories.CarRepository;
import com.guysfromusa.carsgame.repositories.GameRepository;
import com.guysfromusa.carsgame.repositories.MovementsHistoryRepository;
import com.guysfromusa.carsgame.v1.model.Point;
import com.guysfromusa.carsgame.validator.BusinessValidator;
import com.guysfromusa.carsgame.validator.subject.CarGameAdditionValidationSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.guysfromusa.carsgame.entities.enums.CarType.MONSTER;
import static com.guysfromusa.carsgame.entities.enums.CarType.RACER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private MovementsHistoryRepository movementsHistoryRepository;

    @Mock
    private List<BusinessValidator<CarGameAdditionValidationSubject>> validators;

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
    public void shouldAddCarToGame(){
        //given
        String carName = "My-Second-Car";
        String carGame = "Car-Game";
        Point startingPoint = new Point(1,1);
        CarEntity carEntity = new CarEntity();
        carEntity.setName(carName);
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName("game1");
        GameState gameState = new GameState(carGame, null, 30);

        when(gameRepository.findByName(any())).thenReturn(Optional.of(new GameEntity()));
        when(carRepository.findByName(eq(carName))).thenReturn(Optional.of(carEntity));

        when(gameRepository.findByName(any())).thenReturn(Optional.of(gameEntity));

        //when
        carService.addCarToGame(carName, gameState, startingPoint);

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
    public void shouldCrashAndRemoveFromGame() {
        //given
        CarDto car = CarDto.builder()
                .name("skoda")
                .type(RACER)
                .crashed(true)
                .build();

        CarEntity carEntity = new CarEntity();
        when(carRepository.findByGameAndName("game1", "skoda"))
                .thenReturn(Optional.of(carEntity));

        //when
        carService.crashAndRemoveFromGame("game1", car);

        //then
        assertThat(carEntity.isCrashed()).isTrue();
        assertThat(carEntity.getGame()).isNull();
        assertThat(carEntity.getPositionX()).isNull();
        assertThat(carEntity.getPositionY()).isNull();
    }

}